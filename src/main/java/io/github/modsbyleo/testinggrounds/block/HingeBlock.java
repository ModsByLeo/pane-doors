package io.github.modsbyleo.testinggrounds.block;

import io.github.modsbyleo.testinggrounds.block.entity.FakePaneBlockEntity;
import io.github.modsbyleo.testinggrounds.block.entity.ModBlockEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Optional;

public final class HingeBlock extends FakePaneBlock {
    public static final BooleanProperty HANDLE = BooleanProperty.of("handle");

    public HingeBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HANDLE, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(HANDLE);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient())
            return ActionResult.PASS;
        if (player.isSneaking()) {
            Optional<FakePaneBlockEntity> entityOptional = world.getBlockEntity(pos, ModBlockEntityTypes.FAKE_PANE);
            if (entityOptional.isEmpty())
                return ActionResult.PASS;
            FakePaneBlockEntity entity = entityOptional.get();
            Identifier paneId = entity.getPaneId();
            if (!FakePaneBlockEntity.EMPTY_PANE_ID.equals(paneId)) {
                entity.setPaneId(FakePaneBlockEntity.EMPTY_PANE_ID);
                player.getInventory().offerOrDrop(new ItemStack(Registry.BLOCK.get(paneId).asItem(), 1));
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        }
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isEmpty())
            return ActionResult.PASS;
        Item item = stack.getItem();
        if (!(item instanceof BlockItem blockItem))
            return ActionResult.PASS;
        Block block = blockItem.getBlock();
        if (!(block instanceof PaneBlock))
            return ActionResult.PASS;
        setPaneId((ServerWorld) world, pos, Registry.BLOCK.getId(block));
        if (!player.isCreative())
            stack.decrement(1);
        return ActionResult.SUCCESS;
    }
}
