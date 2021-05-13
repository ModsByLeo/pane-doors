package io.github.modsbyleo.testinggrounds.util;

import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.NotNull;

public final class DirectionUtil {
    private DirectionUtil() { }

    public static @NotNull Direction.Axis swapXZ(@NotNull Direction.Axis axis) {
        return switch (axis) {
            case X -> Direction.Axis.Z;
            case Z -> Direction.Axis.X;
            default -> axis;
        };
    }

    public static double choose(@NotNull Direction.Axis axis, @NotNull Vec3d vec) {
        return axis.choose(vec.x, vec.y, vec.z);
    }

    public static double choose(@NotNull Direction.Axis axis, @NotNull Vec3i vec) {
        return axis.choose(vec.getX(), vec.getY(), vec.getZ());
    }
}
