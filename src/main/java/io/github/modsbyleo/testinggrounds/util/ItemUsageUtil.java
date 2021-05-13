package io.github.modsbyleo.testinggrounds.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import org.jetbrains.annotations.NotNull;

public final class ItemUsageUtil {
    private ItemUsageUtil() { }

    public static void decrement(@NotNull ItemUsageContext ctx) {
        if (ctx.getWorld().isClient())
            return;
        PlayerEntity player = ctx.getPlayer();
        if (player == null || player.isCreative())
            return;
        player.getStackInHand(ctx.getHand()).decrement(1);
    }
}
