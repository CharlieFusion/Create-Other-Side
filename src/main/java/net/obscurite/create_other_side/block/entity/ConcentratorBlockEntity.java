package net.obscurite.create_other_side.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.obscurite.create_other_side.density.ChunkDensity;
import net.obscurite.create_other_side.register.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ConcurrentHashMap;

public class ConcentratorBlockEntity extends BlockEntity {
    public boolean IS_STRUCTURED = false;
    private static final double DENSITY_DECREASE_VALUE = -0.2;
    private static ConcurrentHashMap<Player, Integer> teleport_data = new ConcurrentHashMap<>();


    public ConcentratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CONCENTRATOR_BE.get(), pPos, pBlockState);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (this.IS_STRUCTURED) {
            ChunkPos chunk = new ChunkPos(pPos);
            double value = ChunkDensity.getDensityState(chunk);

            if (value > 0) {
                ChunkDensity.changeData(pLevel, chunk, DENSITY_DECREASE_VALUE);
            }
        }
    }
}
