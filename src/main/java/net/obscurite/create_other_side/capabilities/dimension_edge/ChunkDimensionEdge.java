package net.obscurite.create_other_side.capabilities.dimension_edge;

import net.minecraft.nbt.CompoundTag;

public class ChunkDimensionEdge {
    private String dimEdge = "THE_DIMENSION";

    public String getDimEdge() {
        return dimEdge;
    }

    public void changeDimEdge(String value) {
        this.dimEdge = value;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putString("dimension_edge", dimEdge);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.dimEdge = nbt.getString("dimension_edge");
    }
}
