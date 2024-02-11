package net.obscurite.create_other_side.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.obscurite.create_other_side.block.entity.ConcentratorBlockEntity;
import net.obscurite.create_other_side.capabilities.concentartor_structures.ConcentratorStructuredProvider;
import net.obscurite.create_other_side.register.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class ConcentratorBlock extends BaseEntityBlock {
    private static final int STRUCTURE_OFFSET = 16;

    public ConcentratorBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);

        LevelChunk chunk = pLevel.getChunkAt(pPos);

        BlockEntity check1 = pLevel.getBlockEntity(chunk.getPos().getBlockAt(0, pPos.getY(), 0));
        BlockEntity check2 = pLevel.getBlockEntity(chunk.getPos().getBlockAt(15, pPos.getY(), 0));
        BlockEntity check3 = pLevel.getBlockEntity(chunk.getPos().getBlockAt(0, pPos.getY(), 15));
        BlockEntity check4 = pLevel.getBlockEntity(chunk.getPos().getBlockAt(15, pPos.getY(), 15));

        if (check1 instanceof ConcentratorBlockEntity
        && check2 instanceof ConcentratorBlockEntity
        && check3 instanceof ConcentratorBlockEntity
        && check4 instanceof ConcentratorBlockEntity) {
            chunk.getCapability(ConcentratorStructuredProvider.CONCENTRATOR_IS_STRUCTURED).ifPresent(is_structured -> {
                is_structured.changeIfStructured(true);
                chunk.setUnsaved(true);
            });
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        LevelChunk chunk = pLevel.getChunkAt(pPos);

        //ChunkPos chunk = new ChunkPos(pPos);
        BlockEntity check1 = pLevel.getBlockEntity(chunk.getPos().getBlockAt(0, pPos.getY(), 0));
        BlockEntity check2 = pLevel.getBlockEntity(chunk.getPos().getBlockAt(15, pPos.getY(), 0));
        BlockEntity check3 = pLevel.getBlockEntity(chunk.getPos().getBlockAt(0, pPos.getY(), 15));
        BlockEntity check4 = pLevel.getBlockEntity(chunk.getPos().getBlockAt(15, pPos.getY(), 15));

        if (check1.getBlockPos() == pPos ||
            check2.getBlockPos() == pPos ||
            check3.getBlockPos() == pPos ||
            check4.getBlockPos() == pPos) {
            chunk.getCapability(ConcentratorStructuredProvider.CONCENTRATOR_IS_STRUCTURED).ifPresent(is_structured -> {
                is_structured.changeIfStructured(false);
                chunk.setUnsaved(true);
            });
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.CONCENTRATOR_BE.get(),
                (level, blockPos, blockState, pBlockEntity) -> pBlockEntity.tick(level, blockPos, blockState));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ConcentratorBlockEntity(blockPos, blockState);
    }
}
