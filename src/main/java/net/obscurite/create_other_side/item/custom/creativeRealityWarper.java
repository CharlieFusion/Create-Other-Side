package net.obscurite.create_other_side.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.obscurite.create_other_side.density.ChunkDensity;
import net.obscurite.create_other_side.density.ChunkEvents;

public class creativeRealityWarper extends Item {
    public static final String TAG_VALUE = "value";

    public creativeRealityWarper(Item.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide()) {
            Player player = pContext.getPlayer();

            var stack = player.getItemInHand(pContext.getHand());
            CompoundTag tag = stack.getOrCreateTag();
            double newNum = 0.0;

            if (player.isShiftKeyDown()) {
                if (tag.contains(TAG_VALUE)) {
                    double oldNum = tag.getDouble(TAG_VALUE);
                    newNum = oldNum + 5.0;
                    if (newNum > 25) {
                        newNum = -25.0;
                    }
                }

                tag.putDouble(TAG_VALUE, newNum);

                player.displayClientMessage(Component.translatable(
                                "tooltip.create_other_side.creative_reality_warper.change_value", newNum),
                        true);
            } else {
                if (tag.contains(TAG_VALUE)) {
                    newNum = tag.getDouble(TAG_VALUE);
                }

                ChunkDensity.changeData(pContext.getLevel(), player.chunkPosition(), newNum);

                player.displayClientMessage(
                        Component.translatable(
                                "tooltip.create_other_side.creative_reality_warper.value_edited",
                                "" + player.chunkPosition(),
                                ChunkDensity.getDensityState(player.chunkPosition())),
                        true);


            }
        }

        return InteractionResult.SUCCESS;
    }
}
