package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MathHelper;

public class LayerCape implements LayerRenderer<AbstractClientPlayer> {
	private final RenderPlayer playerRenderer;

	public LayerCape(final RenderPlayer playerRendererIn)
	{ this.playerRenderer = playerRendererIn; }

	@Override
	public void doRenderLayer(final AbstractClientPlayer entitylivingbaseIn, final float p_177141_2_, final float p_177141_3_, final float partialTicks, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float scale) {
		if (entitylivingbaseIn.hasPlayerInfo() && !entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isWearing(EnumPlayerModelParts.CAPE) && entitylivingbaseIn.getLocationCape() != null) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableLighting();
			this.playerRenderer.bindTexture(entitylivingbaseIn.getLocationCape());
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, 0.0F, 0.125F);
			final double d0 = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * partialTicks
					- (entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * partialTicks);
			final double d1 = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * partialTicks
					- (entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * partialTicks);
			final double d2 = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * partialTicks
					- (entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * partialTicks);
			final float f = entitylivingbaseIn.prevRotationYawHead + (entitylivingbaseIn.rotationYawHead - entitylivingbaseIn.prevRotationYawHead) * partialTicks;
			final double d3 = MathHelper.sin(f * (float) Math.PI / 180.0F);
			final double d4 = (-MathHelper.cos(f * (float) Math.PI / 180.0F));
			float f1 = (float) d1 * 10.0F;
			f1 = MathHelper.clamp_float(f1, -6.0F, 32.0F);
			float f2 = (float) (d0 * d3 + d2 * d4) * 100.0F;
			final float f3 = (float) (d0 * d4 - d2 * d3) * 100.0F;
			if (f2 < 0.0F) f2 = 0.0F;
			if (f2 > 165.0F) f2 = 165.0F;
			if (f1 < -5.0F) f1 = -5.0F;
			final float f4 = entitylivingbaseIn.prevCameraYaw + (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks;
			f1 = f1 + MathHelper.sin((entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;
			if (entitylivingbaseIn.isSneaking()) {
				f1 += 25.0F;
				if (entitylivingbaseIn.capabilities.isFlying) f1 += 5;
				GlStateManager.translate(0.0F, 0.142F, -0.0178F);
			}
			GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			this.playerRenderer.getMainModel().renderCape(0.0625F);
			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
	}

	@Override
	public boolean shouldCombineTextures() { return false; }
}
