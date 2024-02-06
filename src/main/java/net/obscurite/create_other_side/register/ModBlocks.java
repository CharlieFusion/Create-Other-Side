package net.obscurite.create_other_side.register;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.obscurite.create_other_side.Create_Other_Side;
import net.obscurite.create_other_side.block.ConcentratorBlock;

import java.util.function.Supplier;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Create_Other_Side.MOD_ID);

    public static final RegistryObject<Block> ROSE_QUARTZ_BLOCK = registerBlock("rose_quartz_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK)));

    public static final RegistryObject<Block> CONCENTRATOR = registerBlock("concentrator",
            () -> new ConcentratorBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);

        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
