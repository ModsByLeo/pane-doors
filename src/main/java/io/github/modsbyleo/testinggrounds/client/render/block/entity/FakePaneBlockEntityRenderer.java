package io.github.modsbyleo.testinggrounds.client.render.block.entity;

import io.github.modsbyleo.testinggrounds.block.ModBlocks;
import io.github.modsbyleo.testinggrounds.block.entity.FakePaneBlockEntity;
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

import java.util.Random;

@Environment(EnvType.CLIENT)
public final class FakePaneBlockEntityRenderer implements BlockEntityRenderer<FakePaneBlockEntity> {
    private final BlockRenderManager renderManager;
    private final Random random;

    public FakePaneBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        renderManager = ctx.getRenderManager();
        random = new Random();
    }

    @Override
    public void render(FakePaneBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockRenderView world = entity.getWorld();
        if (world == null)
            return;
        BlockState state = entity.getRenderState();
        BlockPos pos = entity.getPos();
        RenderLayer renderLayer = RenderLayers.getBlockLayer(state);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
        BakedModel model = renderManager.getModel(state);
        renderManager.getModelRenderer().render(world, model, state, pos, matrices, vertexConsumer, false, random, state.getRenderingSeed(pos), OverlayTexture.DEFAULT_UV);

        BlockState actualState = entity.getCachedState();
        if (actualState.isOf(ModBlocks.HINGE)) {
            // TODO render hinge and handle models
        }
    }
}
