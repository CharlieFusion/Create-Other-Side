package net.obscurite.create_other_side.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.obscurite.create_other_side.register.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class ConcentratorBlockEntity extends BlockEntity {

    public ConcentratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CONCENTRATOR_BE.get(), pPos, pBlockState);
    }
}
