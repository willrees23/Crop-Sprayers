package com.github.willrees23.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class CustomHeadUtil {

    private final String BASE_URL = "https://textures.minecraft.net/texture/";

    // pulls the skin URL out of a decoded {"textures":{"SKIN":{"url":"..."}}} payload
    private final Pattern SKIN_URL = Pattern.compile("\"url\"[ \t]*:[ \t]*\"([^\"]+)\"");

    /**
     * Builds a player head wearing the given texture.
     *
     * @param texture either the bare texture hash, a full textures.minecraft.net
     *                URL, or the base64 "value" string that head sites hand out
     */
    public static ItemStack createCustomSkull(String texture) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        URL skinUrl = resolveSkinUrl(texture);

        // derived from the texture, so the same skin always reuses one profile
        // id and the client can cache it instead of refetching per item
        PlayerProfile profile = Bukkit.createPlayerProfile(
                UUID.nameUUIDFromBytes(skinUrl.toString().getBytes(StandardCharsets.UTF_8)));

        profile.getTextures().setSkin(skinUrl);

        meta.setOwnerProfile(profile);
        item.setItemMeta(meta);

        return item;
    }

    private static URL resolveSkinUrl(String texture) {
        if (texture == null || texture.isBlank()) {
            throw new IllegalArgumentException("Texture must not be empty");
        }

        String trimmed = texture.trim();
        String url;

        if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) {
            url = trimmed;
        } else if (isTextureHash(trimmed)) {
            url = BASE_URL + trimmed;
        } else {
            // base64 texture value: decode it and read the URL back out, rather
            // than pasting the whole blob onto BASE_URL (which 404s and leaves
            // the head showing the default Steve skin)
            url = extractUrlFromValue(trimmed);
        }

        // Mojang serves the textures over https; the encoded values still say http
        if (url.startsWith("http://")) {
            url = "https://" + url.substring("http://".length());
        }

        try {
            return URI.create(url).toURL();
        } catch (MalformedURLException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid texture URL: " + url, e);
        }
    }

    private static boolean isTextureHash(String value) {
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            boolean hex = (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
            if (!hex) return false;
        }
        return !value.isEmpty();
    }

    private static String extractUrlFromValue(String value) {
        String decoded;
        try {
            decoded = new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Texture is neither a hash, a URL, nor base64: " + value, e);
        }

        Matcher matcher = SKIN_URL.matcher(decoded);
        if (!matcher.find()) {
            throw new IllegalArgumentException("No skin URL in texture value: " + decoded);
        }
        return matcher.group(1);
    }
}
