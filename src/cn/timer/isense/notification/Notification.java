package cn.timer.isense.notification;

import cn.timer.isense.utils.GradientBlur;
import cn.timer.isense.utils.RenderUtil;
import cn.timer.isense.utils.RoundedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

public class Notification {
    private final String text;
    private int leftTime = 5000;
    private float targetX, targetY, currentX, currentY;
    @SuppressWarnings("FieldCanBeLocal")
    public int width = 70;
    @SuppressWarnings("FieldCanBeLocal")
    private static final int height = 15;

    public Notification(String text) {
        this.text = text;
        beginTime = System.currentTimeMillis();
    }

    public int getWidth() {
        width = mc.fontRendererObj.getStringWidth(text) + 6;
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static int delay = 5000;

    private final Minecraft mc = Minecraft.getMinecraft();
    private final GradientBlur blur = new GradientBlur();
    private final long beginTime;

    public void render() {
        ScaledResolution sr = new ScaledResolution(mc);
        currentX = animation(targetX, currentX, 10);
        currentY = animation(targetY, currentY, 10);
        leftTime = (int) (5000 - (System.currentTimeMillis() - beginTime));
        if (leftTime <= 1000) {
            setTargetX(sr.getScaledWidth() + 2);
        }
        FontRenderer font = mc.fontRendererObj;
        blur.set((int) currentX, (int) currentY, width, height, 1);
        blur.PostGetPixels();
        blur.Update();
        width = font.getStringWidth(text) + 6;
        if (mc.gameSettings.ofFastRender) {
            RenderUtil.drawFastRoundedRect(currentX, currentY, currentX + width, currentY + height, 5, 0x80000000);
            //Gui.instance.drawGradientRect2(currentX, currentY, width, height, blur.getTColor().getRGB(), blur.getBColor().getRGB());
        } else
            RoundedUtil.drawGradientVertical(currentX, currentY, width, height, 5, blur.getBColor(), blur.getTColor());
        font.drawString(text, currentX + 3, currentY + 3, -1);
    }

    private float animation(float target, float current, @SuppressWarnings("SameParameterValue") float speed) {
        current += (target - current) / (100 / speed);
        return current;
    }

    public void setLeftTime(int leftTime) {
        this.leftTime = leftTime;
    }

    public int getLeftTime() {
        return leftTime;
    }

    public void setTargetX(float targetX) {
        this.targetX = targetX;
    }

    public void setTargetY(float targetY) {
        this.targetY = targetY;
    }

    public void setCurrentX(float currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(float currentY) {
        this.currentY = currentY;
    }
}
