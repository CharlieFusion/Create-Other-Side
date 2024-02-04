package net.obscurite.create_other_side.density;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;

public class ChunkDensity {
    private static final String KEY = "chunkDensity";
    // ConcurrentHashMap to allow multiple access at the same time
    private static ConcurrentHashMap<ChunkPos, Double> data = new ConcurrentHashMap<>();

    public static void outputDensityState(ChunkPos position, Player player, CompoundTag compound) {
        player.displayClientMessage(
                Component.translatable(
                    "tooltip.create_other_side.reality_meter.get_value",
                    "" + position, compound.getCompound(KEY).getDouble(position.toString())),
                false);
    }

    public static void readData(LevelAccessor world, ChunkPos key, CompoundTag compound) {
        if(data.containsKey(key)) {
            data.put(key, compound.getCompound(KEY).getDouble(key.toString()));
        } else {
            data.put(key, 100.0);
        }
    }

    public static void freeData(LevelAccessor world, ChunkPos key) {
        // no clue how this should work
    }

    public static void saveData(LevelAccessor world, ChunkPos chunkpos, CompoundTag compound) {
        CompoundTag nbt = new CompoundTag();
        data.forEach((key, value) -> {
            nbt.putDouble(key.toString(), value);
        });

        compound.put(KEY, nbt);
    }

    public static void changeData(LevelAccessor world, ChunkPos key, CompoundTag compound, double value) {
        double new_value;
        if(data.containsKey(key)) {
            new_value = compound.getCompound(KEY).getDouble(key.toString()) + value;
        } else {
            new_value = 100.0 + value;
        }
        new_value =
                new_value <= 100.0 && new_value >= 0.0 ? new_value : Math.min(Math.max(new_value, 0.0), 100.0);
        data.put(key, new_value);
        saveData(world, key, compound);
    }
}
