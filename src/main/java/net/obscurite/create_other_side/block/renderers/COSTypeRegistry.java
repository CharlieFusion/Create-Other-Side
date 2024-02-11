package net.obscurite.create_other_side.block.renderers;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.registry.client.LodestoneShaderRegistry;
import team.lodestar.lodestone.systems.rendering.StateShards;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeProvider;

public class COSTypeRegistry extends RenderStateShard {
    public COSTypeRegistry(String pName, Runnable pSetupState, Runnable pClearState) {
        super(pName, pSetupState, pClearState);
    }
    public static final RenderTypeProvider TEXTURE_ACTUAL_TRIANGLE =
            new RenderTypeProvider((texture) ->
                    LodestoneRenderTypeRegistry.createGenericRenderType(
                            texture.getNamespace() + ":texture_actual_triangle",
                            DefaultVertexFormat.POSITION_COLOR_TEX,
                            VertexFormat.Mode.TRIANGLES,
                            LodestoneRenderTypeRegistry.builder()
                                    .setShaderState(RenderStateShard.POSITION_COLOR_TEX_LIGHTMAP_SHADER)
                                    .setTransparencyState(StateShards.NO_TRANSPARENCY)
                                    .setTextureState(texture)
                                    ));
    public static final RenderTypeProvider TEXTURE_ACTUAL_TRIANGLE_TRANSPARENT =
            new RenderTypeProvider((texture) ->
                    LodestoneRenderTypeRegistry.createGenericRenderType(
                            texture.getNamespace() + ":texture_actual_triangle_transparent",
                            DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
                            VertexFormat.Mode.TRIANGLES,
                            LodestoneRenderTypeRegistry.builder()
                                    .setShaderState(LodestoneShaderRegistry.LODESTONE_TEXTURE.getShard())
                                    .setTransparencyState(StateShards.NORMAL_TRANSPARENCY)
                                    .setTextureState(texture)));
    public static final RenderTypeProvider TEXTURE_ACTUAL_TRIANGLE_ADDITIVE =
            new RenderTypeProvider((texture) ->
                    LodestoneRenderTypeRegistry.createGenericRenderType(
                            texture.getNamespace() + ":texture_actual_triangle_transparent",
                            DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
                            VertexFormat.Mode.TRIANGLES,
                            LodestoneRenderTypeRegistry.builder()
                                    .setShaderState(LodestoneShaderRegistry.LODESTONE_TEXTURE.getShard())
                                    .setTransparencyState(StateShards.ADDITIVE_TRANSPARENCY)
                                    .setTextureState(texture)));

}
