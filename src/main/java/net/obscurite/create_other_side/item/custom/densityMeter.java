package net.obscurite.create_other_side.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.obscurite.create_other_side.density.ChunkDensity;

public class densityMeter extends Item {
    public densityMeter(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(!pContext.getLevel().isClientSide()) {
            Player player = pContext.getPlayer();

            assert player != null;
            ChunkPos chunkpos = player.chunkPosition();
            player.displayClientMessage(
                    Component.translatable(
                            "tooltip.create_other_side.reality_meter.get_value",
                            "" + chunkpos, ChunkDensity.getDensityState(chunkpos)),
                    false);

            System.out.println(ChunkDensity.data);
        }

        return InteractionResult.SUCCESS;
    }
}
