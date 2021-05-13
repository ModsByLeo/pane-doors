package io.github.modsbyleo.testinggrounds.block;

import io.github.modsbyleo.testinggrounds.block.entity.DoorComponentBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

public final class DoorComponentBlock extends BlockWithEntity {
    public enum ComponentType implements StringIdentifiable {
        NONE("none"),
        HANDLE("handle"),
        HINGE("hinge");

        private final @NotNull String id;

        ComponentType(@NotNull String id) {
            this.id = id;
        }

        @Override
        public @NotNull String asString() {
            return id;
        }
    }

    public static final EnumProperty<ComponentType> LEFT_COMPONENT = EnumProperty.of("left_component", ComponentType.class);
    public static final EnumProperty<ComponentType> RIGHT_COMPONENT = EnumProperty.of("right_component", ComponentType.class);
    public static final BooleanProperty THIN_DOUBLE = BooleanProperty.of("thin_double");
    public static final EnumProperty<Direction.Axis> AXIS = EnumProperty.of("axis", Direction.Axis.class, Direction.Axis.X, Direction.Axis.Z);

    public DoorComponentBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState()
                .with(LEFT_COMPONENT, ComponentType.NONE)
                .with(RIGHT_COMPONENT, ComponentType.NONE)
                .with(THIN_DOUBLE, false)
                .with(AXIS, Direction.Axis.X));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEFT_COMPONENT, RIGHT_COMPONENT, THIN_DOUBLE, AXIS);
    }

    @Override
    public @NotNull BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DoorComponentBlockEntity(pos, state);
    }

    // TODO collision/outline shapes
}
