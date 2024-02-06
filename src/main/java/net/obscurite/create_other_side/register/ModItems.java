package net.obscurite.create_other_side.register;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.obscurite.create_other_side.Create_Other_Side;
import net.obscurite.create_other_side.item.custom.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Create_Other_Side.MOD_ID);
    public static final RegistryObject<Item> REALITY_METER = ITEMS.register("reality_meter",
            () -> new densityMeter(new Item.Properties()));
    public static final RegistryObject<Item> CREATIVE_REALITY_WARPER = ITEMS.register("creative_reality_warper",
            () -> new creativeRealityWarper(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
