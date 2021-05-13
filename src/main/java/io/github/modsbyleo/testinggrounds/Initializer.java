package io.github.modsbyleo.testinggrounds;

import io.github.modsbyleo.testinggrounds.block.ModBlocks;
import io.github.modsbyleo.testinggrounds.block.entity.ModBlockEntityTypes;
import io.github.modsbyleo.testinggrounds.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public final class Initializer implements ModInitializer {
    public static final String MOD_ID = "testing-grounds";
    public static final String MOD_NAME = "Testing Grounds";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        ModBlocks.register();
        ModBlockEntityTypes.register();
        ModItems.register();
        log(Level.INFO, "Initialized!");
    }

    public static @NotNull Identifier id(@NotNull String path) {
        return new Identifier(MOD_ID, path);
    }

    public static void log(@NotNull Level level, @NotNull String msg) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + msg);
    }
}