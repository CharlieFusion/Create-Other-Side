package net.obscurite.create_other_side.block.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.obscurite.create_other_side.Create_Other_Side;
import net.obscurite.create_other_side.block.entity.ConcentratorBlockEntity;
import net.minecraft.util.Mth;
import net.minecraft.util.ByIdMap;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeProvider;
import team.lodestar.lodestone.systems.rendering.rendeertype.ShaderUniformHandler;

import java.awt.*;

public class ConcentratorRenderer implements BlockEntityRenderer<ConcentratorBlockEntity> {
    public ConcentratorRenderer(BlockEntityRendererProvider.Context context) {
    }
    @Override
    public void render(ConcentratorBlockEntity concentratorBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        spawnExampleParticles(poseStack);
    }
    public static void spawnExampleParticles(PoseStack poseStack) {
        int red = 180;
        int green = 80;
        int blue = 255;

        ResourceLocation VIGNETTE = new ResourceLocation(Create_Other_Side.MOD_ID, "textures/block/vignette.png");
        ResourceLocation SOLID = new ResourceLocation(Create_Other_Side.MOD_ID, "textures/block/white.png");

        float height = 0.75f;
        float width = 1.5f;

//        VertexConsumer solidTextureConsumer = RenderHandler.DELAYED_RENDER.getBuffer(COSTypeRegistry.TEXTURE_ACTUAL_TRIANGLE.apply(SOLID));
        VertexConsumer solidTextureConsumer = RenderHandler.DELAYED_RENDER.getBuffer(COSTypeRegistry.TEXTURE_ACTUAL_TRIANGLE.apply(SOLID));
        VertexConsumer textureConsumer = RenderHandler.DELAYED_RENDER.getBuffer(COSTypeRegistry.TEXTURE_ACTUAL_TRIANGLE_TRANSPARENT.apply(VIGNETTE));

        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();

        poseStack.translate(0f, 3f, 0f);

        //poseStack.pushPose();

//
//        builder.setColor(new Color(blue, red, green).darker())
//                .setAlpha(1f)
//                .renderSphere(
//                        solidTextureConsumer,
//                        poseStack,
//                        1,
//                        8,        // Mth.clamp(15, 15, 50),
//                        8);             // Mth.clamp(15, 15, 50));

        //poseStack.popPose();
        poseStack.pushPose();
        poseStack.scale(-2f, -2f, -2f);

        builder.setColor(new Color(red, green, blue))
                .setAlpha(.5f)
                .renderSphere(
                        textureConsumer,
                        poseStack,
                        1,
                        8,
                        8);
//        builder.renderQuad(textureConsumer, poseStack, positions, 1f);
//        builder.setPosColorLightmapDefaultFormat();
        poseStack.popPose();

    }
}
