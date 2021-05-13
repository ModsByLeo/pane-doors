package io.github.modsbyleo.testinggrounds.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import static io.github.modsbyleo.testinggrounds.Initializer.id;

public final class DoorComponentBlockEntity extends BlockEntity {
    public static final Identifier EMPTY_PANE_ID = id("empty");

    private @NotNull Identifier paneId;

    public DoorComponentBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.DOOR_COMPONENT, pos, state);
        paneId = EMPTY_PANE_ID;
    }

    public @NotNull Identifier getPaneId() {
        return paneId;
    }

    public void setPaneId(@NotNull Identifier paneId) {
        this.paneId = paneId;
        markDirty();
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
        String paneIdStr;
        if ("minecraft".equals(paneId.getNamespace()))
            paneIdStr = paneId.getPath();
        else
            paneIdStr = paneId.toString();
        nbt.putString("pane_id", paneIdStr);
        return nbt;
    }
}
