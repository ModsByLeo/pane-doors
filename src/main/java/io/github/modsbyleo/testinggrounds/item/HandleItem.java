package io.github.modsbyleo.testinggrounds.item;

import io.github.modsbyleo.testinggrounds.block.FakePaneBlock;
import io.github.modsbyleo.testinggrounds.block.HingeBlock;
import io.github.modsbyleo.testinggrounds.block.ModBlocks;
import io.github.modsbyleo.testinggrounds.util.ItemUsageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public final class HandleItem extends Item {
    public HandleItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        if (ctx.getWorld().isClient())
            return ActionResult.SUCCESS;
        ServerWorld world = (ServerWorld) ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = world.getBlockState(pos);
        if (state.isOf(ModBlocks.HINGE) && !state.get(HingeBlock.HANDLE)) {
            state = state.with(HingeBlock.HANDLE, true);
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
            ItemUsageUtil.decrement(ctx);
            return ActionResult.SUCCESS;
        } else if (state.getBlock() instanceof PaneBlock) {
            // TODO determine handle position correctly
            world.setBlockState(pos, ModBlocks.HANDLE.getDefaultState().with(FakePaneBlock.FACING, ctx.getPlayerFacing().getOpposite()),
                    Block.NOTIFY_ALL);
            FakePaneBlock.setPaneId(world, pos, Registry.BLOCK.getId(state.getBlock()));
            ItemUsageUtil.decrement(ctx);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public String getTranslationKey() {
        return ModBlocks.HANDLE.getTranslationKey();
    }
}
