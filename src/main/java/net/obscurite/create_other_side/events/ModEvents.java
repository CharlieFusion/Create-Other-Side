package net.obscurite.create_other_side.events;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.obscurite.create_other_side.Create_Other_Side;
import net.obscurite.create_other_side.block.entity.ConcentratorBlockEntity;
import net.obscurite.create_other_side.capabilities.concentartor_structures.ConcentratorStructured;
import net.obscurite.create_other_side.capabilities.concentartor_structures.ConcentratorStructuredProvider;
import net.obscurite.create_other_side.capabilities.density.ChunkDensity;
import net.obscurite.create_other_side.capabilities.density.ChunkDensityProvider;
import net.obscurite.create_other_side.capabilities.dimension_edge.ChunkDimensionEdge;
import net.obscurite.create_other_side.capabilities.dimension_edge.ChunkDimensionEdgeProvider;
import net.obscurite.create_other_side.worldgen.portal.ModTeleporter;

import java.util.concurrent.ConcurrentHashMap;

public class ModEvents {

    public static class Events {
        private static ConcurrentHashMap<Player, Integer> teledata = new ConcurrentHashMap<>();
        private static int TELEPORT_TIME = 10; // in seconds

        @SubscribeEvent
        public void onPlayerTick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;
            LevelChunk chunk = player.level().getChunk(player.chunkPosition().x, player.chunkPosition().z);

            if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide()) {
                chunk.getCapability(ChunkDensityProvider.CHUNK_DENSITY).ifPresent(chunkDensity -> {
                    if (chunkDensity.getDensity() == 0.0) {
                        var value = teledata.putIfAbsent(player, 0);
                        if (value == null) value = 0;
                        teledata.put(player, value + 1);
                        if (value == TELEPORT_TIME * 20) { // TELEPORT_TIME (secs) * 40 = N (ticks)
                            chunk.getCapability(ChunkDimensionEdgeProvider.DIMENSION_EDGE).ifPresent(dimEdge -> {
                                if (dimEdge.getDimEdge().equals("NETHER")) {
                                    player.changeDimension(player.getServer().getLevel(Level.NETHER),
                                            new ModTeleporter(player.chunkPosition().getMiddleBlockPosition(16),
                                                    true));
                                } else if (dimEdge.getDimEdge().equals("END")) {
                                    player.changeDimension(player.getServer().getLevel(Level.END));
                                } else if (dimEdge.getDimEdge().equals("THE_DIMENSION")) {
                                    player.teleportRelative(16, 16, 16);
                                    player.sendSystemMessage(Component.literal("Teleportation Success"));
                                }
                            });
                        }
                        player.addEffect(
                                new MobEffectInstance(MobEffects.CONFUSION, 5 * 20, 1,
                                        false, false, false));
                    } else teledata.remove(player);
                });
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Create_Other_Side.MOD_ID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onAttachCapabilitiesChunk(AttachCapabilitiesEvent<LevelChunk> event) {
            if (event.getObject() instanceof LevelChunk) {
                if (!event.getObject().getCapability(ChunkDensityProvider.CHUNK_DENSITY).isPresent()) {
                    event.addCapability(new ResourceLocation(Create_Other_Side.MOD_ID, "density"), new ChunkDensityProvider());
                }
                if (!event.getObject().getCapability(ChunkDimensionEdgeProvider.DIMENSION_EDGE).isPresent()) {
                    event.addCapability(new ResourceLocation(Create_Other_Side.MOD_ID, "dim_edge"), new ChunkDimensionEdgeProvider());
                }
                if (!event.getObject().getCapability(ConcentratorStructuredProvider.CONCENTRATOR_IS_STRUCTURED).isPresent()) {
                    event.addCapability(new ResourceLocation(Create_Other_Side.MOD_ID, "concentrator_is_structured"), new ConcentratorStructuredProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(ChunkDensity.class);
            event.register(ChunkDimensionEdge.class);
            event.register(ConcentratorStructured.class);
        }
    }
}
