package net.obscurite.create_other_side.capabilities.concentartor_structures;

import net.minecraft.nbt.CompoundTag;

public class ConcentratorStructured {
    private boolean is_structured = false;

    public boolean getIfStructured() {
        return is_structured;
    }

    public void changeIfStructured(boolean value) {
        this.is_structured = value;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("structured", is_structured);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.is_structured = nbt.getBoolean("structured");
    }
}
