package io.github.modsbyleo.testinggrounds.client.gui.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

import static io.github.modsbyleo.testinggrounds.Initializer.id;

public final class TestHud extends DrawableHelper {
    public static final TestHud INSTANCE = new TestHud();

    private static final Identifier TEXTURE = id("textures/gui/test.png");
    private static final Vec3f ROTATE_2D_AXIS = new Vec3f(0, 0, 1);

    private float rotationAngle;

    private TestHud() { }

    public void render(MatrixStack matrixStack, float tickDelta) {
        Window win = MinecraftClient.getInstance().getWindow();
        int x = win.getScaledWidth() - 20;
        int y = 4;

        rotationAngle = (rotationAngle + 4 * tickDelta) % 360;
        matrixStack.push();
        matrixStack.translate(x + 8, y + 8, 0);
        matrixStack.multiply(new Quaternion(ROTATE_2D_AXIS, rotationAngle, true));

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrixStack, -8, -8, 0, 0, 16, 16);

        matrixStack.pop();
    }
}
