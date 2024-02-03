package net.obscurite.create_other_side.density;

import net.minecraftforge.event.level.ChunkDataEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
public class ChunkEvents {

    @SubscribeEvent
    public void onChunkLoad(ChunkDataEvent.Load event) {
        if(event.getLevel().isClientSide())
            return;

        ChunkDensity.readData(event.getLevel(), event.getChunk().getPos(), event.getData());
    }

    @SubscribeEvent
    public void onChunkUnload(ChunkEvent.Unload event) {
        if(event.getLevel().isClientSide())
            return;

        ChunkDensity.freeData(event.getLevel(), event.getChunk().getPos());
    }

    @SubscribeEvent
    public void onChunkSave(ChunkDataEvent.Save event) {
        if(event.getLevel().isClientSide())
            return;

        ChunkDensity.saveData(event.getLevel(), event.getChunk().getPos(), event.getData());
    }
}
