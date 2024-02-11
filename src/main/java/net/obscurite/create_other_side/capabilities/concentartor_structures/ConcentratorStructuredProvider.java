package net.obscurite.create_other_side.capabilities.concentartor_structures;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.obscurite.create_other_side.capabilities.density.ChunkDensity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConcentratorStructuredProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<ConcentratorStructured> CONCENTRATOR_IS_STRUCTURED = CapabilityManager.get(new CapabilityToken<ConcentratorStructured>() { });

    private ConcentratorStructured is_structured = null;
    private final LazyOptional<ConcentratorStructured> optional = LazyOptional.of(this::createIsStructured);

    private ConcentratorStructured createIsStructured() {
        if (this.is_structured == null) {
            this.is_structured = new ConcentratorStructured();
        }

        return this.is_structured;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == CONCENTRATOR_IS_STRUCTURED) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createIsStructured().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createIsStructured().loadNBTData(nbt);
    }

}

