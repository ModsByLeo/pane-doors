package io.github.modsbyleo.testinggrounds.client.render.block.entity;

import io.github.modsbyleo.testinggrounds.block.entity.DoorComponentBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

import java.util.HashSet;
import java.util.Random;

@Environment(EnvType.CLIENT)
public final class DoorComponentBlockEntityRenderer implements BlockEntityRenderer<DoorComponentBlockEntity> {
    private static final HashSet<DoorComponentBlockEntity> ERROR_BLOCK_ENTITIES = new HashSet<>();

    private final BlockRenderManager renderManager;
    private final Random random;

    public DoorComponentBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        renderManager = ctx.getRenderManager();
        random = new Random();
    }

    @Override
    public void render(DoorComponentBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!entity.hasWorld())
            return;
        BlockRenderView world = entity.getWorld();
        BlockState state = entity.getRenderState();
        BlockPos pos = entity.getPos();
        RenderLayer renderLayer = RenderLayers.getBlockLayer(state);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
        BakedModel model = renderManager.getModel(state);
        renderManager.getModelRenderer().render(world, model, state, pos, matrices, vertexConsumer, false, random, state.getRenderingSeed(pos), OverlayTexture.DEFAULT_UV);
    }
}
