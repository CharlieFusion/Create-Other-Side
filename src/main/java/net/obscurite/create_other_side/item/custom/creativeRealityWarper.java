package net.obscurite.create_other_side.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.obscurite.create_other_side.density.ChunkDensity;

public class creativeRealityWarper extends Item {
    public creativeRealityWarper(Item.Properties pProperties) {
        super(pProperties);
    }

    // TODO: Make sneaking to change the value of warping
    // TODO: Make functionality of clicking and warping

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(!pContext.getLevel().isClientSide()) {
            Player player = pContext.getPlayer();

            assert player != null;
            ChunkPos chunkpos = player.chunkPosition();
            CompoundTag compound = pContext.getPlayer().getPersistentData();

            ChunkDensity.readData(pContext.getLevel(), chunkpos, compound);
            ChunkDensity.outputDensityState(chunkpos, player, compound);
        }

        return InteractionResult.SUCCESS;
    }
}
