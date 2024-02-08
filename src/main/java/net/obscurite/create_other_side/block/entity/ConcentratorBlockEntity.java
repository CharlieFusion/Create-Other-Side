package net.obscurite.create_other_side.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.obscurite.create_other_side.density.ChunkDensity;
import net.obscurite.create_other_side.register.ModBlockEntities;

import java.util.concurrent.ConcurrentHashMap;
public class ConcentratorBlockEntity extends BlockEntity {
    public boolean IS_STRUCTURED = false;
    private static final double DENSITY_DECREASE_VALUE = -0.2;
    private static ConcurrentHashMap<Player, Integer> teleport_data = new ConcurrentHashMap<>();


    public ConcentratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CONCENTRATOR_BE.get(), pPos, pBlockState);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        ChunkPos chunk = new ChunkPos(pPos);

        if (ChunkDensity.getConcentrationState(chunk)) {
            Block check1 = pLevel.getBlockState(chunk.getBlockAt(0, pPos.getY() - 1, 0)).getBlock();
            Block check2 = pLevel.getBlockState(chunk.getBlockAt(15, pPos.getY() - 1, 0)).getBlock();
            Block check3 = pLevel.getBlockState(chunk.getBlockAt(0, pPos.getY() - 1, 15)).getBlock();
            Block check4 = pLevel.getBlockState(chunk.getBlockAt(15, pPos.getY() - 1, 15)).getBlock();

            var map = ChunkDensity.getDefaultPortals();

            if (check1 == check2 && check1 == check3 && check1 == check4) {

                if (check1.equals(Blocks.OBSIDIAN)) {
                    map.put("NETHER", true);
                    map.put("THE_DIMENSION", false);
                }
                else if (check1.equals(Blocks.ANCIENT_DEBRIS)) {
                    map.put("END", true);
                    map.put("THE_DIMENSION", false);
                }
            }
            var tp_data = ChunkDensity.getTeleportationData();
            tp_data.put(chunk, map);
            ChunkDensity.setTeleportationData(tp_data);

            double value = ChunkDensity.getDensityState(chunk);

            if (value > 0) {
                ChunkDensity.changeData(pLevel, chunk, DENSITY_DECREASE_VALUE);
            }
        }
    }
}
