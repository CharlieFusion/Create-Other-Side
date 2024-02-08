package net.obscurite.create_other_side.density;
import net.minecraft.world.level.ChunkPos;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ChunkDensity {
    private static final String KEY = "chunkDensity";
    private static final String KEY_TELEPORT = "chunkDimensionEdge";
    private static final String KEY_CONCENTRATOR = "chunkIsStructured";
    // ConcurrentHashMap to allow multiple access at the same time
    private static ConcurrentHashMap<ChunkPos, Double> density_data = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<ChunkPos, Boolean> concentration_data = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<ChunkPos, HashMap<String, Boolean>> teleportation_data = new ConcurrentHashMap<>();

    public static HashMap<String, Boolean> getDefaultPortals() {
        HashMap<String, Boolean> default_portals = new HashMap<>();

        default_portals.put("NETHER", false);
        default_portals.put("END", false);
        default_portals.put("THE_DIMENSION", true);

        return default_portals;
    }

    public static double getDensityState(ChunkPos position) {
        return density_data.getOrDefault(position, 100.0);
    }
    public static boolean getConcentrationState(ChunkPos position) {
        return concentration_data.getOrDefault(position, false);
    }
    public static void setConcentrationState(ChunkPos position, Boolean value) {
        ChunkDensity.concentration_data.put(position, value);
    }

    public static ConcurrentHashMap<ChunkPos, HashMap<String, Boolean>> getTeleportationData() {
        return teleportation_data;
    }

    public static void setTeleportationData(ConcurrentHashMap<ChunkPos, HashMap<String, Boolean>> teleportation_data) {
        ChunkDensity.teleportation_data = teleportation_data;
    }

    public static void readData(LevelAccessor world, ChunkPos key, CompoundTag compound) {
        HashMap<String, Boolean> default_portals = getDefaultPortals();

        concentration_data.put(key, false);
        if (compound.contains(KEY_CONCENTRATOR)) {
            if (compound.getCompound(KEY_CONCENTRATOR).contains(key.toString())) {
                concentration_data.put(key, compound.getCompound(KEY_CONCENTRATOR).getBoolean(key.toString()));
            }
        }

        teleportation_data.put(key, default_portals);
        if (compound.contains(KEY_TELEPORT)) {
            if (compound.getCompound(KEY_TELEPORT).contains(key.toString())) {
                for (String nbt_key : compound.getCompound(KEY_TELEPORT).getCompound(key.toString()).getAllKeys()) {
                    default_portals.put(nbt_key, compound.getCompound(KEY_TELEPORT).getCompound(key.toString()).getBoolean(nbt_key));
                }

                teleportation_data.put(key, default_portals);
            }
        }

        density_data.put(key, 100.0);
        if (compound.contains(KEY)) {
            if (compound.getCompound(KEY).contains(key.toString())) {
                density_data.put(key, compound.getCompound(KEY).getDouble(key.toString()));
            }
        }
    }

    public static void freeData(LevelAccessor world, ChunkPos key) {
        teleportation_data.remove(key);
        density_data.remove(key);
    }

    public static void saveData(LevelAccessor world, ChunkPos key, CompoundTag compound) {
        CompoundTag densityNBT = new CompoundTag();
        CompoundTag concentrationNBT = new CompoundTag();
        CompoundTag portalNBT = new CompoundTag();

        concentration_data.forEach((pos, value) -> {
            concentrationNBT.putBoolean(pos.toString(), value);
        });

        teleportation_data.forEach((pos, map) -> {
            CompoundTag snbt = new CompoundTag();
            map.forEach((map_key, map_value) -> {
                snbt.putBoolean(map_key, map_value);
            });
            portalNBT.put(pos.toString(), snbt);
        });

        density_data.forEach((pos, value) -> {
            densityNBT.putDouble(pos.toString(), value);
        });

        compound.put(KEY, densityNBT);
        compound.put(KEY_TELEPORT, portalNBT);
        compound.put(KEY_CONCENTRATOR, concentrationNBT);
        // {KEY_TELEPORT: {Position: {Nether: false, End: false, Dim: false}}}
    }

    public static void changeData(LevelAccessor world, ChunkPos key, double value) {
        double new_value;
        new_value = getDensityState(key) + value;
        new_value = new_value <= 100.0 && new_value >= 0.0 ?
                new_value : Math.min(Math.max(new_value, 0.0), 100.0);
        density_data.put(key, new_value);

        // A crutch to save the chunk
        BlockState oldState = world.getBlockState(key.getBlockAt(0, 0, 0));
        world.setBlock(key.getBlockAt(0, 0, 0), Blocks.DIAMOND_BLOCK.defaultBlockState(), 1);
        world.setBlock(key.getBlockAt(0, 0, 0), oldState, 1);
    }
}
