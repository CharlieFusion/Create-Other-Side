package net.obscurite.create_other_side.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.chunk.LevelChunk;
import net.obscurite.create_other_side.capabilities.density.ChunkDensityProvider;

public class densityMeter extends Item {
    public densityMeter(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(!pContext.getLevel().isClientSide()) {
            Player player = pContext.getPlayer();

            LevelChunk chunk = pContext.getLevel().getChunk(player.chunkPosition().x, player.chunkPosition().z);

            chunk.getCapability(ChunkDensityProvider.CHUNK_DENSITY).ifPresent(density -> {
                player.displayClientMessage(
                        Component.translatable(
                                "tooltip.create_other_side.reality_meter.get_value",
                                "" + player.chunkPosition(),
                                density.getDensity()),
                        true);
            });

//            assert player != null;
//            ChunkPos chunkpos = player.chunkPosition();
//            player.displayClientMessage(
//                    Component.translatable(
//                            "tooltip.create_other_side.reality_meter.get_value",
//                            "" + chunkpos, ChunkDensity.getDensityState(chunkpos)),
//                    false);
        }

        return InteractionResult.SUCCESS;
    }
}
