package io.github.modsbyleo.testinggrounds.block.entity;

import io.github.modsbyleo.testinggrounds.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import static io.github.modsbyleo.testinggrounds.Initializer.id;

public final class ModBlockEntityTypes {
    private ModBlockEntityTypes() { }

    public static final BlockEntityType<DoorComponentBlockEntity> DOOR_COMPONENT =
            FabricBlockEntityTypeBuilder.create(DoorComponentBlockEntity::new, ModBlocks.DOOR_COMPONENT).build();

    public static void register() {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, id("door_component"), DOOR_COMPONENT);
    }
}
