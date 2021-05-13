package io.github.modsbyleo.testinggrounds.item;

import io.github.modsbyleo.testinggrounds.Initializer;
import io.github.modsbyleo.testinggrounds.block.ModBlocks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

import static io.github.modsbyleo.testinggrounds.Initializer.id;

public final class ModItems {
    private ModItems() { }

    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(Items.STICK));

    private static final FabricItemSettings SETTINGS = new FabricItemSettings().group(GROUP);
    public static final BlockItem HINGE = new BlockItem(ModBlocks.HINGE, SETTINGS);
    public static final HandleItem HANDLE = new HandleItem(SETTINGS);

    public static void register() {
        Registry.register(Registry.ITEM, id("hinge"), HINGE);
        Registry.register(Registry.ITEM, id("handle"), HANDLE);
    }
}
