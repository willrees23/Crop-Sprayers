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
    WHEAT(Material.WHEAT, Material.HAY_BLOCK),
    CARROT(Material.CARROTS, Material.CARROT),
    POTATO(Material.POTATOES, Material.POTATO);

    private final Material material;
    private final Material displayItem;

    CropType(Material material, Material displayItem) {
        this.material = material;
        this.displayItem = displayItem;
    }
}
