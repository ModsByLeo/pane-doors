package io.github.modsbyleo.testinggrounds.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.util.registry.Registry;

import static io.github.modsbyleo.testinggrounds.Initializer.id;

public final class ModBlocks {
    private ModBlocks() { }

    public static final DoorComponentBlock DOOR_COMPONENT = new DoorComponentBlock(FabricBlockSettings.of(Material.GLASS).nonOpaque());

    public static void register() {
        Registry.register(Registry.BLOCK, id("door_component"), DOOR_COMPONENT);
    }
}
