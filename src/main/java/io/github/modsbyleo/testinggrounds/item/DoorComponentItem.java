package io.github.modsbyleo.testinggrounds.item;

import io.github.modsbyleo.testinggrounds.block.DoorComponentBlock;
import io.github.modsbyleo.testinggrounds.block.ModBlocks;
import io.github.modsbyleo.testinggrounds.block.entity.DoorComponentBlockEntity;
import io.github.modsbyleo.testinggrounds.block.entity.ModBlockEntityTypes;
import io.github.modsbyleo.testinggrounds.util.DirectionUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static io.github.modsbyleo.testinggrounds.Initializer.log;

public abstract class DoorComponentItem extends Item {
    public DoorComponentItem(Settings settings) {
        super(settings);
    }

    public abstract boolean updateDoorComponent(@NotNull ItemUsageContext ctx, @NotNull BlockState state,
                                                @NotNull DoorComponentBlockEntity blockEntity);

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient())
            return ActionResult.SUCCESS;
        BlockState state = context.getWorld().getBlockState(context.getBlockPos());
        Block currentBlock = state.getBlock();
        if (currentBlock instanceof PaneBlock) {
            ServerWorld world = (ServerWorld) context.getWorld();
            BlockPos pos = context.getBlockPos();
            Direction.Axis axis = context.getSide().getAxis();
            if (axis == Direction.Axis.Y) {
                if (context.getPlayer() != null)
                    context.getPlayer().sendMessage(new LiteralText("Can't place component on top or bottom!"), true);
                return ActionResult.FAIL;
            }
            axis = DirectionUtil.swapXZ(axis);
            state = ModBlocks.DOOR_COMPONENT.getDefaultState().with(DoorComponentBlock.AXIS, axis);
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
            DoorComponentBlockEntity blockEntity = new DoorComponentBlockEntity(pos, ModBlocks.DOOR_COMPONENT.getDefaultState());
            blockEntity.setPaneId(Registry.BLOCK.getId(currentBlock));
            boolean success = updateDoorComponent(context, state, blockEntity);
            world.addBlockEntity(blockEntity);
            if (success) {
                decrementItem(context);
                return ActionResult.SUCCESS;
            }
        } else if (currentBlock instanceof DoorComponentBlock) {
            ServerWorld world = (ServerWorld) context.getWorld();
            BlockPos pos = context.getBlockPos();
            Optional<DoorComponentBlockEntity> optBE = world.getBlockEntity(pos, ModBlockEntityTypes.DOOR_COMPONENT);
            if (optBE.isPresent()) {
                if (updateDoorComponent(context, state, optBE.get())) {
                    decrementItem(context);
                    return ActionResult.SUCCESS;
                }
            } else
                log(Level.ERROR, "Door component block at " + worldPosStr(world, pos) + " has no block entity!");
        }
        return ActionResult.FAIL;
    }

    private void decrementItem(@NotNull ItemUsageContext ctx) {
        if (ctx.getPlayer() == null)
            return;
        PlayerEntity player = ctx.getPlayer();
        if (player.isCreative())
            return;
        player.getStackInHand(ctx.getHand()).decrement(1);
    }

    private static @NotNull String worldPosStr(@NotNull World world, @NotNull BlockPos pos) {
        return "<world:'" + world + "'," +
                "dim:'" + world.getRegistryKey().getValue() + "'>" +
                "@ (" + pos.toShortString() + ")";
    }

    protected static boolean placeComponent(@NotNull ItemUsageContext ctx, @NotNull BlockState state,
                                         @NotNull DoorComponentBlock.ComponentType toPlace) {
        Direction.Axis axis = DirectionUtil.swapXZ(ctx.getPlayerFacing().getAxis());
        if (state.get(DoorComponentBlock.THIN_DOUBLE)
                || axis != state.get(DoorComponentBlock.AXIS)) {
            if (ctx.getPlayer() != null)
                ctx.getPlayer().sendMessage(new LiteralText("Can't place component here!"), true);
            return false;
        }
        Vec3d hitPos = ctx.getHitPos();
        double hitAt = DirectionUtil.choose(axis, hitPos) - DirectionUtil.choose(axis, ctx.getBlockPos());
        log(Level.INFO, hitPos + ", hit at " + hitAt);
        EnumProperty<DoorComponentBlock.ComponentType> prop
                = hitAt <= 0.5 ? DoorComponentBlock.LEFT_COMPONENT : DoorComponentBlock.RIGHT_COMPONENT;
        DoorComponentBlock.ComponentType cType = state.get(prop);
        if (cType != DoorComponentBlock.ComponentType.NONE) {
            if (ctx.getPlayer() != null)
                ctx.getPlayer().sendMessage(new LiteralText("Can't place component here!"), true);
            return false;
        }
        ctx.getWorld().setBlockState(ctx.getBlockPos(), state.with(prop, toPlace), Block.NOTIFY_ALL);
        return true;
    }
}
