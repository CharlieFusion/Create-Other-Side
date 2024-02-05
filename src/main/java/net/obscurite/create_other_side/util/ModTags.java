package net.obscurite.create_other_side.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.obscurite.create_other_side.Create_Other_Side;

public class ModTags {
    public static class Blocks {
        // here's go tags
        // public static final TagKey<Block> NAME_TAG = tag("tag_name");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Create_Other_Side.MOD_ID, name));
        }
    }
    public static class Items {

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(Create_Other_Side.MOD_ID, name));
        }
    }
}
