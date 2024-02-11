package net.obscurite.create_other_side.capabilities.density;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChunkDensityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<ChunkDensity> CHUNK_DENSITY = CapabilityManager.get(new CapabilityToken<ChunkDensity>() { });

    private ChunkDensity density = null;
    private final LazyOptional<ChunkDensity> optional = LazyOptional.of(this::createChunkDensity);

    private ChunkDensity createChunkDensity() {
        if (this.density == null) {
            this.density = new ChunkDensity();
        }

        return this.density;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == CHUNK_DENSITY) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createChunkDensity().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createChunkDensity().loadNBTData(nbt);
    }

}

