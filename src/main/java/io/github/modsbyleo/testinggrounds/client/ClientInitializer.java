package io.github.modsbyleo.testinggrounds.client;

import io.github.modsbyleo.testinggrounds.client.gui.hud.TestHud;
import io.github.modsbyleo.testinggrounds.client.render.block.entity.ModBlockEntityRenderers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import static io.github.modsbyleo.testinggrounds.Initializer.MOD_NAME;

@Environment(EnvType.CLIENT)
public final class ClientInitializer implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "|Client");

    @Override
    public void onInitializeClient() {
        ModBlockEntityRenderers.register();
        HudRenderCallback.EVENT.register(TestHud.INSTANCE::render);
        log(Level.INFO, "Initialized on the client!");
    }

    public static void log(@NotNull Level level, @NotNull String msg) {
        LOGGER.log(level, "[" + MOD_NAME + "|Client] " + msg);
    }
}