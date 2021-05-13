package io.github.modsbyleo.testinggrounds.block;

import io.github.modsbyleo.testinggrounds.block.entity.FakePaneBlockEntity;
import io.github.modsbyleo.testinggrounds.block.entity.ModBlockEntityTypes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class FakePaneBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    public FakePaneBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    public static void setFakePane(@NotNull ServerWorld world, @NotNull BlockPos pos, @NotNull Direction facing, @NotNull Identifier paneId) {
        world.setBlockState(pos, ModBlocks.FAKE_PANE.getDefaultState().with(FACING, facing), NOTIFY_ALL);
        setPaneId(world, pos, paneId);
    }

    public static void setPaneId(@NotNull ServerWorld world, @NotNull BlockPos pos, @NotNull Identifier paneId) {
        world.getBlockEntity(pos, ModBlockEntityTypes.FAKE_PANE).ifPresentOrElse(entity -> entity.setPaneId(paneId), () -> {
            FakePaneBlockEntity entity = new FakePaneBlockEntity(pos, world.getBlockState(pos));
            entity.setPaneId(paneId);
            world.addBlockEntity(entity);
        });
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @NotNull BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FakePaneBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        super.onSyncedBlockEvent(state, world, pos, type, data);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.onSyncedBlockEvent(type, data);
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    // TODO collision/outline shapes
}
