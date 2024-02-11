package net.obscurite.create_other_side.capabilities.dimension_edge;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChunkDimensionEdgeProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<ChunkDimensionEdge> DIMENSION_EDGE = CapabilityManager.get(new CapabilityToken<ChunkDimensionEdge>() { });

    private ChunkDimensionEdge dimEdge = null;
    private final LazyOptional<ChunkDimensionEdge> optional = LazyOptional.of(this::createChunkDimEdge);

    private ChunkDimensionEdge createChunkDimEdge() {
        if (this.dimEdge == null) {
            this.dimEdge = new ChunkDimensionEdge();
        }

        return this.dimEdge;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == DIMENSION_EDGE) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createChunkDimEdge().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createChunkDimEdge().loadNBTData(nbt);
    }

}

