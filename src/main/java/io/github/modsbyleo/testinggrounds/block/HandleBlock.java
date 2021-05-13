package io.github.modsbyleo.testinggrounds.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.NotNull;

public final class HandleBlock extends FakePaneBlock {
    public enum Position implements StringIdentifiable {
        LEFT("left"),
        RIGHT("right");

        private final @NotNull String id;

        Position(@NotNull String id) {
            this.id = id;
        }

        @Override
        public @NotNull String asString() {
            return id;
        }
    }

    public static final EnumProperty<Position> POSITION = EnumProperty.of("position", Position.class);

    public HandleBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(POSITION, Position.LEFT));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(POSITION);
    }
}
