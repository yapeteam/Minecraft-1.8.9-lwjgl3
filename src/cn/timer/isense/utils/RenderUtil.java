package cn.timer.isense.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static net.minecraft.client.renderer.GlStateManager.disableBlend;
import static net.minecraft.client.renderer.GlStateManager.enableTexture2D;
import static org.lwjgl.opengl.GL11.*;

public class RenderUtil {
    public static void drawRect(float left, float top, float right, float bottom, final int color) {
        if (left < right) {
            final float e = left;
            left = right;
            right = e;
        }
        if (top < bottom) {
            final float e = top;
            top = bottom;
            bottom = e;
        }
        final float a = (color >> 24 & 0xFF) / 255.0f;
        final float b = (color >> 16 & 0xFF) / 255.0f;
        final float c = (color >> 8 & 0xFF) / 255.0f;
        final float d = (color & 0xFF) / 255.0f;
        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(b, c, d, a);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(left, bottom, 0.0).endVertex();
        worldRenderer.pos(right, bottom, 0.0).endVertex();
        worldRenderer.pos(right, top, 0.0).endVertex();
        worldRenderer.pos(left, top, 0.0).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawTexturedRect(final float x, final float y, final float width, final float height, final String image) {
        GL11.glPushMatrix();
        final boolean enableBlend = GL11.glIsEnabled(3042);
        final boolean disableAlpha = !GL11.glIsEnabled(3008);
        if (!enableBlend) {
            GL11.glEnable(3042);
        }
        if (!disableAlpha) {
            GL11.glDisable(3008);
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("iSense/textures/" + image + ".png"));
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, (int) width, (int) height, width, height);
        if (!enableBlend) {
            GL11.glDisable(3042);
        }
        if (!disableAlpha) {
            GL11.glEnable(3008);
        }
        GL11.glPopMatrix();
    }

    public static void drawShadow(final float x, final float y, final float width, final float height) {
        drawTexturedRect(x - 9.0f, y - 9.0f, 9.0f, 9.0f, "paneltopleft");
        drawTexturedRect(x - 9.0f, y + height, 9.0f, 9.0f, "panelbottomleft");
        drawTexturedRect(x + width, y + height, 9.0f, 9.0f, "panelbottomright");
        drawTexturedRect(x + width, y - 9.0f, 9.0f, 9.0f, "paneltopright");
        drawTexturedRect(x - 9.0f, y, 9.0f, height, "panelleft");
        drawTexturedRect(x + width, y, 9.0f, height, "panelright");
        drawTexturedRect(x, y - 9.0f, width, 9.0f, "paneltop");
        drawTexturedRect(x, y + height, width, 9.0f, "panelbottom");
    }

    public static void drawFastRoundedRect(float left, float top, float right, float bottom, float radius, int color) {
        float f2 = (color >> 24 & 0xFF) / 255.0f;
        float f3 = (color >> 16 & 0xFF) / 255.0f;
        float f4 = (color >> 8 & 0xFF) / 255.0f;
        float f5 = (color & 0xFF) / 255.0f;
        glDisable(2884);
        glDisable(3553);
        glEnable(3042);
        glBlendFunc(770, 771);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        glColor4f(f3, f4, f5, f2);
        glBegin(5);
        glVertex2f(left + radius, top);
        glVertex2f(left + radius, bottom);
        glVertex2f(right - radius, top);
        glVertex2f(right - radius, bottom);
        glEnd();
        glBegin(5);
        glVertex2f(left, top + radius);
        glVertex2f(left + radius, top + radius);
        glVertex2f(left, bottom - radius);
        glVertex2f(left + radius, bottom - radius);
        glEnd();
        glBegin(5);
        glVertex2f(right, top + radius);
        glVertex2f(right - radius, top + radius);
        glVertex2f(right, bottom - radius);
        glVertex2f(right - radius, bottom - radius);
        glEnd();
        glBegin(6);
        float f6 = right - radius;
        float f7 = top + radius;
        glVertex2f(f6, f7);
        int j;
        for (j = 0; j <= 18; ++j) {
            float f8 = j * 5.0f;
            glVertex2f((float) (f6 + radius * Math.cos(Math.toRadians(f8))), (float) (f7 - radius * Math.sin(Math.toRadians(f8))));
        }
        glEnd();
        glBegin(6);
        f6 = left + radius;
        f7 = top + radius;
        glVertex2f(f6, f7);
        for (j = 0; j <= 18; ++j) {
            float f9 = j * 5.0f;
            glVertex2f((float) (f6 - radius * Math.cos(Math.toRadians(f9))), (float) (f7 - radius * Math.sin(Math.toRadians(f9))));
        }
        glEnd();
        glBegin(6);
        f6 = left + radius;
        f7 = bottom - radius;
        glVertex2f(f6, f7);
        for (j = 0; j <= 18; ++j) {
            float f10 = j * 5.0f;
            glVertex2f((float) (f6 - radius * Math.cos(Math.toRadians(f10))), (float) (f7 + radius * Math.sin(Math.toRadians(f10))));
        }
        glEnd();
        glBegin(6);
        f6 = right - radius;
        f7 = bottom - radius;
        glVertex2f(f6, f7);
        for (j = 0; j <= 18; ++j) {
            float f11 = j * 5.0f;
            glVertex2f((float) (f6 + radius * Math.cos(Math.toRadians(f11))), (float) (f7 + radius * Math.sin(Math.toRadians(f11))));
        }
        glEnd();
        glEnable(3553);
        glEnable(2884);
        glDisable(3042);
        enableTexture2D();
        disableBlend();
        glColor4f(1, 1, 1, 1);
    }
}
