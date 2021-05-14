package io.github.modsbyleo.testinggrounds.block.entity;

import io.github.modsbyleo.testinggrounds.block.FakePaneBlock;
import io.github.modsbyleo.testinggrounds.block.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

import static io.github.modsbyleo.testinggrounds.Initializer.id;
import static io.github.modsbyleo.testinggrounds.client.ClientInitializer.log;

public final class FakePaneBlockEntity extends BlockEntity implements BlockEntityClientSerializable {
    public static final Identifier EMPTY_PANE_ID = id("empty");
    @Environment(EnvType.CLIENT)
    private static final HashSet<FakePaneBlockEntity> ERROR_BLOCK_ENTITIES = new HashSet<>();

    private @NotNull Identifier paneId;
    private BlockState renderState;

    public FakePaneBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.FAKE_PANE, pos, state);
        paneId = EMPTY_PANE_ID;
    }

    public @NotNull Identifier getPaneId() {
        return paneId;
    }

    public void setPaneId(@NotNull Identifier paneId) {
        Identifier oldPaneId = this.paneId;
        this.paneId = paneId;
        if (!oldPaneId.equals(paneId)) {
            invalidateRenderState();
            markDirtyAndSync();
        }
    }

    @Environment(EnvType.CLIENT)
    public @NotNull BlockState getRenderState() {
        if (renderState == null) {
            Block block = Registry.BLOCK.get(getPaneId());
            if (!(block instanceof PaneBlock)) {
                if (ERROR_BLOCK_ENTITIES.add(this))
                    log(Level.ERROR, getPaneId() + " ain't a pane block (" + getPos().toShortString() + ")");
                return Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, getCachedState().get(FakePaneBlock.FACING));
            }
            ERROR_BLOCK_ENTITIES.remove(this);
            renderState = block.getDefaultState();
            if (getCachedState().isOf(ModBlocks.HINGE)) {
                // hinge's facing is where the _hinge_ is facing, not the pane itself
                // pane is perpendicular to the hinge
                renderState = switch (getCachedState().get(FakePaneBlock.FACING)) {
                    case NORTH, SOUTH -> renderState.with(PaneBlock.NORTH, true).with(PaneBlock.SOUTH, true);
                    case EAST, WEST -> renderState.with(PaneBlock.EAST, true).with(PaneBlock.WEST, true);
                    default -> renderState;
                };
            } else {
                // for standard fake panes (and handle), facing is where the pane is facing
                renderState = switch (getCachedState().get(FakePaneBlock.FACING)) {
                    case NORTH, SOUTH -> renderState.with(PaneBlock.EAST, true).with(PaneBlock.WEST, true);
                    case EAST, WEST -> renderState.with(PaneBlock.NORTH, true).with(PaneBlock.SOUTH, true);
                    default -> renderState;
                };
            }
        }
        return renderState;
    }

    public void invalidateRenderState() {
        renderState = null;
    }

    private void markDirtyAndSync() {
        markDirty();
        if (world != null && !world.isClient())
            sync();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("pane_id", NbtElement.STRING_TYPE)) {
            Identifier paneIdMaybe = Identifier.tryParse(nbt.getString("pane_id"));
            if (paneIdMaybe != null)
                setPaneId(paneIdMaybe);
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        writeNbt0(nbt);
        return nbt;
    }

    private void writeNbt0(NbtCompound nbt) {
        String paneIdStr;
        if ("minecraft".equals(paneId.getNamespace()))
            paneIdStr = paneId.getPath();
        else
            paneIdStr = paneId.toString();
        nbt.putString("pane_id", paneIdStr);
    }

    @Override
    public void fromClientTag(NbtCompound tag) {
        readNbt(tag);
        invalidateRenderState();
    }

    @Override
    public NbtCompound toClientTag(NbtCompound tag) {
        writeNbt0(tag);
        return tag;
    }
}
