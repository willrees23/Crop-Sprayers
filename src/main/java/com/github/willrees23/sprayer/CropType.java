package com.github.willrees23.sprayer;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum CropType {

    /*
     * Two materials per crop, because they are different things in Minecraft:
     *
     *  material    - the BLOCK that gets planted (plural ids). Block-only:
     *                minecraft:carrots and minecraft:potatoes have no item
     *                form at all, so they cannot be put in an inventory slot.
     *  displayItem - the ITEM shown on the visual's armour stand head
     *                (singular ids). Wearable, unlike the crop blocks.
     */
    WHEAT(Material.WHEAT, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGM4NTZiZDViMGY1ZmQyMDU2M2JmNjhiMWZlM2E1ODcyM2QzYWY5Y2FlZjg1OTIyMWM3ZWNmYTk2NDJmZjE5YiJ9fX0="),
    CARROT(Material.CARROTS, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGEzODkwYWE4Yzc2ZjE3N2NiYWU3ZGMwOTY2MTQ5YTJlMGZiMWMxZWZlYjI4M2RlYjdkZWFhMDlmZDBmYjYifX19"),
    POTATO(Material.POTATOES, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmNhZTVkMDM3OWZlNzNiOTQwNDIxNzhkODdkOTgyOThkM2NkZTdkYTEyNmIxOGRjY2U0YTNhNmM2NjNmYWZiNyJ9fX0=");

    private final Material material;
    private final String displayTexture;

    CropType(Material material, String displayTexture) {
        this.material = material;
        this.displayTexture = displayTexture;
    }
}
