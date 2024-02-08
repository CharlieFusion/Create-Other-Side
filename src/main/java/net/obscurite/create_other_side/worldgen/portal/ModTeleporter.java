package net.obscurite.create_other_side.worldgen.portal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.ITeleporter;
import net.obscurite.create_other_side.block.ConcentratorBlock;
import net.obscurite.create_other_side.register.ModBlocks;

import java.util.function.Function;

public class ModTeleporter implements ITeleporter {
    public static BlockPos thisPos = BlockPos.ZERO;
    public static boolean insideDimension = true;

    public ModTeleporter(BlockPos pos, boolean insideDim) {
        thisPos = pos;
        insideDimension = insideDim;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld,
                              float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);
        int x = thisPos.getX() << 2;
        int y = 32;
        int z = thisPos.getX() << 2;

        if (!insideDimension) {
            y = thisPos.getY();
        } else {
            x = thisPos.getX() >> 2;
            z = thisPos.getX() >> 2;
        }

        BlockPos destinationPos = new BlockPos(x, y, z);

        for (BlockPos checkPos : BlockPos.betweenClosed(destinationPos.west(8),
                destinationPos.above(50).east(8))) {
            if ((destinationWorld.getBlockState(checkPos).getBlock() == Blocks.AIR) &&
                    !(destinationWorld.getBlockState(checkPos).getBlock() instanceof LiquidBlock) &&
                    (destinationWorld.getBlockState(checkPos.above()).getBlock() == Blocks.AIR) &&
                    !(destinationWorld.getBlockState(checkPos.above()).getBlock() instanceof LiquidBlock) &&
                    !(destinationWorld.getBlockState(checkPos.below()).getBlock() instanceof LiquidBlock)) {
                destinationPos = checkPos;
                break;
            }
        }

        if (destinationWorld.getBlockState(destinationPos.below()).getBlock() == Blocks.AIR ||
                destinationWorld.getBlockState(destinationPos.below()).getBlock() instanceof LiquidBlock) {
            destinationWorld.setBlock(destinationPos.below(), Blocks.OBSIDIAN.defaultBlockState(), 1);
            destinationWorld.setBlock(destinationPos.below().north(), Blocks.OBSIDIAN.defaultBlockState(), 1);
            destinationWorld.setBlock(destinationPos.below().north().east(), Blocks.OBSIDIAN.defaultBlockState(), 1);
            destinationWorld.setBlock(destinationPos.below().east(), Blocks.OBSIDIAN.defaultBlockState(), 1);
            destinationWorld.setBlock(destinationPos.below().south().east(), Blocks.OBSIDIAN.defaultBlockState(), 1);
            destinationWorld.setBlock(destinationPos.below().south(), Blocks.OBSIDIAN.defaultBlockState(), 1);
            destinationWorld.setBlock(destinationPos.below().south().west(), Blocks.OBSIDIAN.defaultBlockState(), 1);
            destinationWorld.setBlock(destinationPos.below().west(), Blocks.OBSIDIAN.defaultBlockState(), 1);
            destinationWorld.setBlock(destinationPos.below().north().west(), Blocks.OBSIDIAN.defaultBlockState(), 1);
        }
        entity.setPos(destinationPos.getX(), destinationPos.getY(), destinationPos.getZ());
        entity.teleportTo(destinationPos.getX(), destinationPos.getY(), destinationPos.getZ());

        return entity;
    }
}
