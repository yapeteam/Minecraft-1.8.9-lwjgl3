package net.minecraft.client.resources;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractResourcePack implements IResourcePack {
	private static final Logger resourceLog = LogManager.getLogger();
	public final File resourcePackFile;
	public static final int SIZE = 64;

	public AbstractResourcePack(final File resourcePackFileIn)
	{ this.resourcePackFile = resourcePackFileIn; }

	private static String locationToName(final ResourceLocation location) { return String.format("%s/%s/%s", "assets", location.getResourceDomain(), location.getResourcePath()); }

	protected static String getRelativeName(final File p_110595_0_, final File p_110595_1_) { return p_110595_0_.toURI().relativize(p_110595_1_.toURI()).getPath(); }

	@Override
	public InputStream getInputStream(final ResourceLocation location) throws IOException { return this.getInputStreamByName(locationToName(location)); }

	@Override
	public boolean resourceExists(final ResourceLocation location) { return this.hasResourceName(locationToName(location)); }

	protected abstract InputStream getInputStreamByName(String name) throws IOException;

	protected abstract boolean hasResourceName(String name);

	protected void logNameNotLowercase(final String name) { resourceLog.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", name, this.resourcePackFile); }

	@Override
	public <T extends IMetadataSection> T getPackMetadata(final IMetadataSerializer metadataSerializer, final String metadataSectionName) throws IOException {
		return readMetadata(metadataSerializer, this.getInputStreamByName("pack.mcmeta"), metadataSectionName);
	}

	static <T extends IMetadataSection> T readMetadata(final IMetadataSerializer p_110596_0_, final InputStream p_110596_1_, final String p_110596_2_) {
		JsonObject jsonobject = null;
		BufferedReader bufferedreader = null;
		try {
			bufferedreader = new BufferedReader(new InputStreamReader(p_110596_1_, Charsets.UTF_8));
			jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
		} catch (final RuntimeException runtimeexception) {
			throw new JsonParseException(runtimeexception);
		} finally {
			IOUtils.closeQuietly(bufferedreader);
		}
		return p_110596_0_.parseMetadataSection(p_110596_2_, jsonobject);
	}

	@Override
	public BufferedImage getPackImage() throws IOException { return this.scalePackImage(TextureUtil.readBufferedImage(this.getInputStreamByName("pack.png"))); }

	public BufferedImage scalePackImage(final BufferedImage image) throws IOException {
		if (image == null) return null;
		BufferedImage smallImage = null;
		smallImage = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
		final Graphics graphics = smallImage.getGraphics();
		graphics.drawImage(image, 0, 0, SIZE, SIZE, null);
		/* I think finalize is more efficient than dispose I could be totally wrong */
		graphics.finalize();
		return smallImage;
	}

	@Override
	public String getPackName() { return this.resourcePackFile.getName(); }
}
