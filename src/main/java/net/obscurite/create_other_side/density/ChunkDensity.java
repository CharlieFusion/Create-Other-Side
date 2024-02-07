package net.obscurite.create_other_side.density;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.jarjar.selection.util.Constants;

public class ChunkDensity {
    private static final String KEY = "chunkDensity";
    // ConcurrentHashMap to allow multiple access at the same time
    public static ConcurrentHashMap<ChunkPos, Double> data = new ConcurrentHashMap<>();

    public static double getDensityState(ChunkPos position) {
        return data.getOrDefault(position, 100.0);
    }

    public static void readData(LevelAccessor world, ChunkPos key, CompoundTag compound) {
        data.put(key, 100.0);
        if (compound.contains(KEY)) {
            if (compound.getCompound(KEY).contains(key.toString())) {
                data.put(key, compound.getCompound(KEY).getDouble(key.toString()));
            }
        }

        System.out.println("<DEBUG LOG> Chunk " + key + " is loaded. Compound: " + getDensityState(key));
    }

    public static void freeData(LevelAccessor world, ChunkPos key) {
        data.remove(key);
    }

    public static void saveData(LevelAccessor world, ChunkPos key, CompoundTag compound) {
        CompoundTag nbt = new CompoundTag();
        data.forEach((pos, value) -> {
            nbt.putDouble(pos.toString(), value);
        });

        compound.put(KEY, nbt);
        System.out.println("<DEBUG LOG> Chunk " + key + " is saved. Compound: " + compound.getCompound(KEY).getDouble(key.toString()));
    }

    public static void changeData(LevelAccessor world, ChunkPos key, double value) {
        double new_value;
        new_value = getDensityState(key) + value;
        new_value = new_value <= 100.0 && new_value >= 0.0 ?
                new_value : Math.min(Math.max(new_value, 0.0), 100.0);
        data.put(key, new_value);

        // A crutch to save the chunk
        BlockState oldState = world.getBlockState(key.getBlockAt(0, 0, 0));
        world.setBlock(key.getBlockAt(0, 0, 0), Blocks.DIAMOND_BLOCK.defaultBlockState(), 1);
        world.setBlock(key.getBlockAt(0, 0, 0), oldState, 1);
    }
}
