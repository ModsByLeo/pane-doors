package io.github.modsbyleo.testinggrounds.client.render.block.entity;

import io.github.modsbyleo.testinggrounds.block.entity.ModBlockEntityTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public final class ModBlockEntityRenderers {
    private ModBlockEntityRenderers() { }

    public static void register() {
        BlockEntityRendererRegistry.INSTANCE.register(ModBlockEntityTypes.FAKE_PANE,
                FakePaneBlockEntityRenderer::new);
    }
}
