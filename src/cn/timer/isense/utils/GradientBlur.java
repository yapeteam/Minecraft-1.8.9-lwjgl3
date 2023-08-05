package cn.timer.isense.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjglx.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;
import java.nio.IntBuffer;

@SuppressWarnings("SpellCheckingInspection")
public class GradientBlur {
    @SuppressWarnings("InnerClassMayBeStatic")
    public class Timer {
        private long lastCheck = getSystemTime();

        public boolean hasReach(float mil) {
            return getTimePassed() >= (mil);
        }

        public void reset() {
            lastCheck = getSystemTime();
        }

        private long getTimePassed() {
            return getSystemTime() - lastCheck;
        }

        private long getSystemTime() {
            return System.nanoTime() / (long) (1E6);
        }
    }

    private int x, y, width, height, delay;
    private final Timer timer = new Timer();
    private int tRed, tGreen, tBlue;
    private int lasttRed, lasttGreen, lasttBlue;
    private int bRed, bGreen, bBlue;
    private int lastbRed, lastbGreen, lastbBlue;
    private int colorTop, colorTopRight, colorBottom, colorBottomRight;

    public void set(int x, int y, int width, int height, int delay) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setDelay(delay);
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void Update() {
        lasttRed = tRed;
        lasttGreen = tGreen;
        lasttBlue = tBlue;

        lastbRed = bRed;
        lastbGreen = bGreen;
        lastbBlue = bBlue;

        Color top = ColorUtil.blend(ColorUtil.colorFromInt(colorTop), ColorUtil.colorFromInt(colorTopRight));
        Color bottom = ColorUtil.blend(ColorUtil.colorFromInt(colorBottom), ColorUtil.colorFromInt(colorBottomRight));

        bRed += ((bottom.getRed() - bRed) / (5)) + 0.1;
        bGreen += ((bottom.getGreen() - bGreen) / (5)) + 0.1;
        bBlue += ((bottom.getBlue() - bBlue) / (5)) + 0.1;

        tRed += ((top.getRed() - tRed) / (5)) + 0.1;
        tGreen += ((top.getGreen() - tGreen) / (5)) + 0.1;
        tBlue += ((top.getBlue() - tBlue) / (5)) + 0.1;

        tRed = Math.min(tRed, 255);
        tGreen = Math.min(tGreen, 255);
        tBlue = Math.min(tBlue, 255);
        tRed = Math.max(tRed, 0);
        tGreen = Math.max(tGreen, 0);
        tBlue = Math.max(tBlue, 0);

        bRed = Math.min(bRed, 255);
        bGreen = Math.min(bGreen, 255);
        bBlue = Math.min(bBlue, 255);
        bRed = Math.max(bRed, 0);
        bGreen = Math.max(bGreen, 0);
        bBlue = Math.max(bBlue, 0);
    }

    public void PostGetPixels() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if (timer.hasReach(delay)) {
            IntBuffer pixelBuffer;
            int[] pixelValues;
            int size = width * height;
            pixelBuffer = BufferUtils.createIntBuffer(size);
            pixelValues = new int[size];
            GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            pixelBuffer.clear();
            GL11.glReadPixels(x * sr.getScaleFactor(), (sr.getScaledHeight() - y) * sr.getScaleFactor(), width, height, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);
            pixelBuffer.get(pixelValues);
            TextureUtil.processPixelValues(pixelValues, width, height);
            colorTop = pixelValues[0];
            colorTopRight = pixelValues[width - 1];
            colorBottom = pixelValues[(height - 1) * width - 1];
            colorBottomRight = pixelValues[height * width - 1];
            timer.reset();
        }
    }

    public int smoothAnimation(double current, double last) {
        return (int) (current * Minecraft.getMinecraft().timer.renderPartialTicks + (last * (1.0f - Minecraft.getMinecraft().timer.renderPartialTicks)));
    }

    public Color getTColor() {
        int tR = smoothAnimation(tRed, lasttRed);
        int tG = smoothAnimation(tGreen, lasttGreen);
        int tB = smoothAnimation(tBlue, lasttBlue);
        return new Color(tR, tG, tB);
    }

    public Color getBColor() {
        int bR = smoothAnimation(bRed, lastbRed);
        int bG = smoothAnimation(bGreen, lastbGreen);
        int bB = smoothAnimation(bBlue, lastbBlue);
        return new Color(bR, bG, bB);
    }
}
