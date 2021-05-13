package io.github.modsbyleo.testinggrounds.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.util.registry.Registry;

import static io.github.modsbyleo.testinggrounds.Initializer.id;

public final class ModBlocks {
    private ModBlocks() { }

    public static final FakePaneBlock FAKE_PANE = new FakePaneBlock(FabricBlockSettings.of(Material.GLASS).nonOpaque());
    public static final HingeBlock HINGE = new HingeBlock(FabricBlockSettings.of(Material.STONE).nonOpaque());
    public static final HandleBlock HANDLE = new HandleBlock(FabricBlockSettings.of(Material.STONE).nonOpaque());

    public static void register() {
        Registry.register(Registry.BLOCK, id("fake_pane"), FAKE_PANE);
        Registry.register(Registry.BLOCK, id("hinge"), HINGE);
        Registry.register(Registry.BLOCK, id("handle"), HANDLE);
    }
}
