package io.github.modsbyleo.testinggrounds.item;

import io.github.modsbyleo.testinggrounds.block.DoorComponentBlock;
import io.github.modsbyleo.testinggrounds.block.entity.DoorComponentBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUsageContext;
import org.jetbrains.annotations.NotNull;

public final class HandleItem extends DoorComponentItem {
    public HandleItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean updateDoorComponent(@NotNull ItemUsageContext ctx, @NotNull BlockState state,
                                    @NotNull DoorComponentBlockEntity blockEntity) {
        return placeComponent(ctx, state, DoorComponentBlock.ComponentType.HANDLE);
    }
}
