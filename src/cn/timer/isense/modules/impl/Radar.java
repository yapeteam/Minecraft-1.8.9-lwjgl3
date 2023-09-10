package cn.timer.isense.modules.impl;

import cn.timer.isense.event.EventTarget;
import cn.timer.isense.event.events.EventRender2D;
import cn.timer.isense.event.events.EventTick;
import cn.timer.isense.modules.AbstractModule;
import cn.timer.isense.utils.GradientBlur;
import cn.timer.isense.utils.RenderUtil;
import cn.timer.isense.utils.RoundedUtil;
import cn.timer.isense.utils.Stencil;
import cn.timer.isense.utils.color.ColorUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import org.lwjglx.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;

public class Radar extends AbstractModule {
    private final float width;
    private final float height;
    private final float x;
    private final float y;
    private final float scale = 60;

    public Radar() {
        super("Radar", Keyboard.KEY_R);
        this.x = 5;
        this.y = 45;
        width = height = scale;
    }

    private final GradientBlur blur = new GradientBlur();

    @EventTarget
    private void onTick(EventTick e) {
        blur.Update();
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        blur.set((int) x, (int) y, (int) width, (int) height, 50);
        blur.PostGetPixels();
        RenderUtil.drawShadow(x, y, width, height);
        if (!mc.gameSettings.ofFastRender)
            RoundedUtil.drawGradientVertical(x, y, width, height, 3, blur.getBColor(), blur.getTColor());
        RenderUtil.drawFastRoundedRect(x - 1, y - 1, x + width + 1, y + height + 1, 3, new Color(2, 1, 1, 94).getRGB());
        RenderUtil.drawRect(x + (scale / 2.0f - 0.5f), y + 3.5f, x + scale / 2.0f + 0.5f, y + scale - 3.5f, new Color(255, 255, 255, 80).getRGB());
        RenderUtil.drawRect(x + 3.5f, y + (scale / 2.0f - 0.5f), x + scale - 3.5f, y + scale / 2 + 0.5f, new Color(255, 255, 255, 80).getRGB());
        float partialTicks = mc.timer.renderPartialTicks;
        ScaledResolution sr = new ScaledResolution(mc);
        Stencil.write(false);
        RenderUtil.drawFastRoundedRect(x - 1, y - 1, x + width + 1, y + height + 1, 3, -1);
        Stencil.erase(true);
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, 0.0);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glTranslated(scale / 2.0, scale / 2.0, 0.0);
        GL11.glRotated(mc.thePlayer.prevRotationYaw + 0.0f * partialTicks, 0.0, 0.0, -1.0);
        GL11.glPointSize((float) (4 * sr.getScaleFactor()));
        GL11.glEnable(2832);
        ColorUtils.glColor(new Color(77, 151, 255).getRGB());
        GL11.glBegin(GL11.GL_POINTS);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        for (Entity entity : getAllMatchedEntity()) {
            final double dx = mc.thePlayer.prevPosX + (mc.thePlayer.posX - mc.thePlayer.prevPosX) * partialTicks - (entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks);
            final double dz = mc.thePlayer.prevPosZ + (mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * partialTicks - (entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks);
            GL11.glVertex2d(dx, dz);
        }
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2832);
        GL11.glColor4f(1, 1, 1, 1);
        Stencil.dispose();
    }

    private Entity[] getAllMatchedEntity() {
        final Entity player = mc.thePlayer;
        if (mc.theWorld != null) {
            ArrayList<Entity> entities = new ArrayList<>(mc.theWorld.loadedEntityList.size());
            double max = scale * scale * 2;
            for (final Entity entity : mc.theWorld.loadedEntityList) {
                if (entity == player) {
                    continue;
                }
                final double dx = player.posX - entity.posX;
                final double dz = player.posZ - entity.posZ;
                final double distance = dx * dx + dz * dz;
                if (distance > max) {
                    continue;
                }
                if (!(entity instanceof EntityLivingBase)) {
                    continue;
                }
                entities.add(entity);
            }
            return entities.toArray(new Entity[0]);
        }
        return new Entity[0];
    }
}
