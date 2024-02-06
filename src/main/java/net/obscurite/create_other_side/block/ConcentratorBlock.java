package net.obscurite.create_other_side.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.obscurite.create_other_side.block.entity.ConcentratorBlockEntity;
import net.obscurite.create_other_side.density.ChunkDensity;
import org.jetbrains.annotations.Nullable;

public class ConcentratorBlock extends BaseEntityBlock {
    public static final int STRUCTURE_OFFSET = 16;

    public ConcentratorBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pPos, BlockState oldState, boolean pIsMoving) {
        super.onPlace(state, world, pPos, oldState, pIsMoving);

        ChunkPos chunk = new ChunkPos(pPos);

        BlockEntity check1 = world.getBlockEntity(chunk.getBlockAt(0, pPos.getY(), 0));
        BlockEntity check2 = world.getBlockEntity(chunk.getBlockAt(15, pPos.getY(), 0));
        BlockEntity check3 = world.getBlockEntity(chunk.getBlockAt(0, pPos.getY(), 15));
        BlockEntity check4 = world.getBlockEntity(chunk.getBlockAt(15, pPos.getY(), 15));

        if (check1 instanceof ConcentratorBlockEntity
        && check2 instanceof ConcentratorBlockEntity
        && check3 instanceof ConcentratorBlockEntity
        && check4 instanceof ConcentratorBlockEntity) {
            ChunkDensity.changeData(world, chunk,
                    world.getNearestPlayer(pPos.getX(), pPos.getY(), pPos.getZ(), 120.0, false).getPersistentData(),
                    -1000);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ConcentratorBlockEntity(blockPos, blockState);
    }

//    @Override
//    public void onPlace(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
//        super.onPlace(world, pos, state, placer, itemStack);
//        if (itemStack.hasCustomName()) {
//            var blockEntity = world.getBlockEntity(pos);
//            if (blockEntity instanceof AstralDisplayBlockEntity astralDisplayBlockEntity) {
//                astralDisplayBlockEntity.setCustomName(itemStack.getName());
//            }
//        }
//        if (state.get(FACING).getAxis().isHorizontal()) {
//            var astralDisplay = world.getBlockEntity(pos);
//            for (var i = 1; i <= 20; i++) {
//                BlockEntity foundAstralDisplay = world.getBlockEntity(pos.offset(state.get(FACING), i));
//                // if astral display found
//                if (astralDisplay instanceof AstralDisplayBlockEntity astralDisplayBlockEntity
//                        && foundAstralDisplay instanceof AstralDisplayBlockEntity foundAstralDisplayBE
//                        && world.getBlockState(pos.offset(state.get(FACING), i)).isOf(ModBlocks.ASTRAL_DISPLAY)) {
//                    Direction direction = world.getBlockState(pos.offset(state.get(FACING), i)).get(FACING);
//
//                    // if same orientation, set to parent
//                    if (direction == state.get(FACING)) {
//                        astralDisplayBlockEntity.setParentPos(foundAstralDisplayBE.getParentPos());
//                    } else { // if different horizontal orientation set to display found
//                        astralDisplayBlockEntity.setParentPos(pos.offset(state.get(FACING), i));
//                    }
//                    return;
//                }
//            }
//        }
//    }
}
