package net.obscurite.create_other_side.density;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.portal.PortalForcer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.level.ChunkDataEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.level.ChunkWatchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.obscurite.create_other_side.worldgen.portal.ModTeleporter;

import java.util.concurrent.ConcurrentHashMap;


public class ChunkEvents {
    private static ConcurrentHashMap<Player, Integer> teledata = new ConcurrentHashMap<>();
    private static int TELEPORT_TIME = 10; // in seconds

    @SubscribeEvent
    public void onChunkLoad(ChunkDataEvent.Load event) {
        if (event.getLevel() != null && !event.getLevel().isClientSide())
            ChunkDensity.readData(event.getLevel(), event.getChunk().getPos(), event.getData());
    }

    @SubscribeEvent
    public void onChunkUnload(ChunkEvent.Unload event) {
        if (event.getLevel() != null && !event.getLevel().isClientSide())
            if (!event.getChunk().isUnsaved())
                ChunkDensity.freeData(event.getLevel(), event.getChunk().getPos());
    }

    @SubscribeEvent
    public void onChunkSave(ChunkDataEvent.Save event) {
        if (event.getLevel() != null && !event.getLevel().isClientSide())
            ChunkDensity.saveData(event.getLevel(), event.getChunk().getPos(), event.getData());
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide()) {
            if (ChunkDensity.getDensityState(player.chunkPosition()) < 5.0) {
                var value = teledata.putIfAbsent(player, 0);
                if (value == null) value = 0;
                teledata.put(player, value + 1);
                if (value == TELEPORT_TIME * 20) { // TELEPORT_TIME (secs) * 40 = N (ticks)
                    if (ChunkDensity.getTeleportationData().get(player.chunkPosition()).get("NETHER")){
                        player.changeDimension(player.getServer().getLevel(Level.NETHER),
                                new ModTeleporter(player.chunkPosition().getMiddleBlockPosition(16),
                                true));
                    } else if (ChunkDensity.getTeleportationData().get(player.chunkPosition()).get("END")){
                        player.changeDimension(player.getServer().getLevel(Level.END));
                    } else if (ChunkDensity.getTeleportationData().get(player.chunkPosition()).get("THE_DIMENSION")){
                        player.teleportRelative(16, 16, 16);
                        player.sendSystemMessage(Component.literal("Teleportation Success"));
                    }
                }
                player.addEffect(
                        new MobEffectInstance(MobEffects.CONFUSION, 5 * 20, 1,
                                false, false, false));
            } else teledata.remove(player);
        }
    }
}
