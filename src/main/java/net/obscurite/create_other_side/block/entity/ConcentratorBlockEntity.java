package net.obscurite.create_other_side.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.obscurite.create_other_side.capabilities.concentartor_structures.ConcentratorStructuredProvider;
import net.obscurite.create_other_side.capabilities.density.ChunkDensity;
import net.obscurite.create_other_side.capabilities.density.ChunkDensityProvider;
import net.obscurite.create_other_side.capabilities.dimension_edge.ChunkDimensionEdgeProvider;
import net.obscurite.create_other_side.register.ModBlockEntities;

import java.util.concurrent.ConcurrentHashMap;
public class ConcentratorBlockEntity extends BlockEntity {
    private static final double DENSITY_DECREASE_VALUE = -0.01;


    public ConcentratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CONCENTRATOR_BE.get(), pPos, pBlockState);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        LevelChunk chunk = pLevel.getChunkAt(pPos);

        chunk.getCapability(ConcentratorStructuredProvider.CONCENTRATOR_IS_STRUCTURED).ifPresent(isStructured -> {
            if (isStructured.getIfStructured()) {
                Block check1 = pLevel.getBlockState(chunk.getPos().getBlockAt(0, pPos.getY() - 1, 0)).getBlock();
                Block check2 = pLevel.getBlockState(chunk.getPos().getBlockAt(15, pPos.getY() - 1, 0)).getBlock();
                Block check3 = pLevel.getBlockState(chunk.getPos().getBlockAt(0, pPos.getY() - 1, 15)).getBlock();
                Block check4 = pLevel.getBlockState(chunk.getPos().getBlockAt(15, pPos.getY() - 1, 15)).getBlock();

                if (check1 == check2 && check1 == check3 && check1 == check4) {
                    chunk.getCapability(ChunkDimensionEdgeProvider.DIMENSION_EDGE).ifPresent(dimEdge -> {
                        if (check1.equals(Blocks.OBSIDIAN)) {
                            dimEdge.changeDimEdge("NETHER");
                        } else if (check1.equals(Blocks.ANCIENT_DEBRIS)) {
                            dimEdge.changeDimEdge("END");
                        } else {
                            dimEdge.changeDimEdge("THE_DIMENSION");
                        }
                    });
                }

                chunk.getCapability(ChunkDensityProvider.CHUNK_DENSITY).ifPresent(density -> {
                    if (density.getDensity() > 0) {
                        density.changeDensity(DENSITY_DECREASE_VALUE);
                    }
                });

                chunk.setUnsaved(true);
            }
        });
    }
}
