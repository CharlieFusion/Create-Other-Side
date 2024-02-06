package net.obscurite.create_other_side.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.obscurite.create_other_side.Create_Other_Side;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Create_Other_Side.MOD_ID);

    public static final RegistryObject<CreativeModeTab> OTHER_SIDE_TAB = CREATIVE_MODE_TABS.register("other_side_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.REALITY_METER.get()))
                    .title(Component.translatable("creativetab.other_side_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.REALITY_METER.get());
                        pOutput.accept(ModItems.CREATIVE_REALITY_WARPER.get());

                        pOutput.accept(ModBlocks.ROSE_QUARTZ_BLOCK.get());
                        pOutput.accept(ModBlocks.CONCENTRATOR.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
