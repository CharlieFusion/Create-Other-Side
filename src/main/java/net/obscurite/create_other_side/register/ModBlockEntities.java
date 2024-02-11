package net.obscurite.create_other_side.register;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.obscurite.create_other_side.Create_Other_Side;
import net.obscurite.create_other_side.block.entity.ConcentratorBlockEntity;
import net.obscurite.create_other_side.block.renderers.ConcentratorRenderer;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Create_Other_Side.MOD_ID);

    public static final RegistryObject<BlockEntityType<ConcentratorBlockEntity>> CONCENTRATOR_BE =
            BLOCK_ENTITIES.register("concentrator_be", () ->
                    BlockEntityType.Builder.of(ConcentratorBlockEntity::new,
                            ModBlocks.CONCENTRATOR.get()).build(null));

//    @Mod.EventBusSubscriber(modid = Create_Other_Side.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
//    public static class ClientOnly {
//        @SubscribeEvent
//        public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
//            event.registerBlockEntityRenderer(CONCENTRATOR_BE.get(), ConcentratorRenderer::new);
//        }
//    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
