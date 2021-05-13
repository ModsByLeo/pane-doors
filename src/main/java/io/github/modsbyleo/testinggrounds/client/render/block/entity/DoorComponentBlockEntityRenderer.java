package io.github.modsbyleo.testinggrounds.client.render.block.entity;

import io.github.modsbyleo.testinggrounds.Initializer;
import io.github.modsbyleo.testinggrounds.block.DoorComponentBlock;
import io.github.modsbyleo.testinggrounds.block.entity.DoorComponentBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;
import org.apache.logging.log4j.Level;

import java.util.Random;

@Environment(EnvType.CLIENT)
public final class DoorComponentBlockEntityRenderer implements BlockEntityRenderer<DoorComponentBlockEntity> {
    private final BlockRenderManager renderManager;
    private final Random random;

    public DoorComponentBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        renderManager = ctx.getRenderManager();
        random = new Random();
    }

    @Override
    public void render(DoorComponentBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        final BlockRenderView world = entity.getWorld();
        Block block = Registry.BLOCK.get(entity.getPaneId());
        if (!(block instanceof PaneBlock)) {
            Initializer.log(Level.ERROR, entity.getPaneId() + " ain't a pane block");
            return;
        }
        BlockState state = block.getDefaultState();
        state = switch (entity.getCachedState().get(DoorComponentBlock.AXIS)) {
            case X -> state.with(PaneBlock.WEST, true).with(PaneBlock.EAST, true);
            case Z -> state.with(PaneBlock.NORTH, true).with(PaneBlock.SOUTH, true);
            default -> state;
        };
        BlockPos pos = entity.getPos();
        RenderLayer renderLayer = RenderLayers.getBlockLayer(state);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
        BakedModel model = renderManager.getModel(state);
        renderManager.getModelRenderer().render(world, model, state, pos, matrices, vertexConsumer, false, random, state.getRenderingSeed(pos), OverlayTexture.DEFAULT_UV);
    }
}
