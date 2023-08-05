package net.optifine;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.optifine.config.ConnectedParser;
import net.optifine.config.EntityClassLocator;
import net.optifine.config.IObjectLocator;
import net.optifine.config.ItemLocator;
import net.optifine.reflect.ReflectorForge;
import net.optifine.util.PropertiesOrdered;
import pisi.unitedmeows.meowlib.math.MeowMath;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DynamicLights {
    private static final DynamicLightsMap mapDynamicLights = new DynamicLightsMap();
    private static final Map<Object, Integer> mapEntityLightLevels = new HashMap<>();
    private static final Map<Object, Integer> mapItemLightLevels = new HashMap<>();
    private static long timeUpdateMs = 0L;
    private static boolean initialized;

    public static void entityRemoved(final Entity entityIn, final RenderGlobal renderGlobal) {
        synchronized (mapDynamicLights) {
            final DynamicLight dynamiclight = mapDynamicLights.remove(entityIn.getEntityId());
            if (dynamiclight != null) {
                dynamiclight.updateLitChunks(renderGlobal);
            }
        }
    }

    public static void update(final RenderGlobal renderGlobal) {
        final long i = System.currentTimeMillis();
        if (i >= timeUpdateMs + 50L) {
            timeUpdateMs = i;
            if (!initialized) {
                initialize();
            }
            synchronized (mapDynamicLights) {
                updateMapDynamicLights(renderGlobal);
                if (mapDynamicLights.size() > 0) {
                    final List<DynamicLight> list = mapDynamicLights.valueList();
                    for (final DynamicLight dynamiclight : list) {
                        dynamiclight.update(renderGlobal);
                    }
                }
            }
        }
    }

    private static void initialize() {
        initialized = true;
        mapEntityLightLevels.clear();
        mapItemLightLevels.clear();
        final String[] astring = ReflectorForge.getForgeModIds();
        for (final String s : astring) {
            try {
                final ResourceLocation resourcelocation = new ResourceLocation(s, "optifine/dynamic_lights.properties");
                final InputStream inputstream = Config.getResourceStream(resourcelocation);
                loadModConfiguration(inputstream, resourcelocation.toString(), s);
            } catch (final IOException ignored) {
            }
        }
        if (mapEntityLightLevels.size() > 0) {
            Config.dbg("DynamicLights entities: " + mapEntityLightLevels.size());
        }
        if (mapItemLightLevels.size() > 0) {
            Config.dbg("DynamicLights items: " + mapItemLightLevels.size());
        }
    }

    private static void loadModConfiguration(final InputStream in, final String path, final String modId) {
        if (in != null) {
            try {
                final Properties properties = new PropertiesOrdered();
                properties.load(in);
                in.close();
                Config.dbg("DynamicLights: Parsing " + path);
                final ConnectedParser connectedparser = new ConnectedParser("DynamicLights");
                loadModLightLevels(properties.getProperty("entities"), mapEntityLightLevels, new EntityClassLocator(), connectedparser, path, modId);
                loadModLightLevels(properties.getProperty("items"), mapItemLightLevels, new ItemLocator(), connectedparser, path, modId);
            } catch (final IOException var5) {
                Config.warn("DynamicLights: Error reading " + path);
            }
        }
    }

    private static void loadModLightLevels(final String prop, final Map<Object, Integer> mapLightLevels, final IObjectLocator ol, final ConnectedParser cp, final String path, final String modId) {
        if (prop != null) {
            final String[] astring = Config.tokenize(prop, " ");
            for (final String s : astring) {
                final String[] astring1 = Config.tokenize(s, ":");
                if (astring1.length != 2) {
                    cp.warn("Invalid entry: " + s + ", in:" + path);
                } else {
                    final String s1 = astring1[0];
                    final String s2 = astring1[1];
                    final String s3 = modId + ":" + s1;
                    final ResourceLocation resourcelocation = new ResourceLocation(s3);
                    final Object object = ol.getObject(resourcelocation);
                    if (object == null) {
                        cp.warn("Object not found: " + s3);
                    } else {
                        final int j = cp.parseInt(s2, -1);
                        if (j >= 0 && j <= 15) {
                            mapLightLevels.put(object, j);
                        } else {
                            cp.warn("Invalid light level: " + s);
                        }
                    }
                }
            }
        }
    }

    private static void updateMapDynamicLights(final RenderGlobal renderGlobal) {
        final World world = renderGlobal.getWorld();
        if (world != null) {
            for (final Entity entity : world.getLoadedEntityList()) {
                final int i = getLightLevel(entity);
                if (i > 0) {
                    final int j = entity.getEntityId();
                    DynamicLight dynamiclight = mapDynamicLights.get(j);
                    if (dynamiclight == null) {
                        dynamiclight = new DynamicLight(entity);
                        mapDynamicLights.put(j, dynamiclight);
                    }
                } else {
                    final int k = entity.getEntityId();
                    final DynamicLight dynamiclight1 = mapDynamicLights.remove(k);
                    if (dynamiclight1 != null) {
                        dynamiclight1.updateLitChunks(renderGlobal);
                    }
                }
            }
        }
    }

    public static int getCombinedLight(final BlockPos pos, int combinedLight) {
        final double d0 = getLightLevel(pos);
        combinedLight = getCombinedLight(d0, combinedLight);
        return combinedLight;
    }

    public static int getCombinedLight(final Entity entity, int combinedLight) {
        final double d0 = getLightLevel(entity);
        combinedLight = getCombinedLight(d0, combinedLight);
        return combinedLight;
    }

    public static int getCombinedLight(final double lightPlayer, int combinedLight) {
        if (lightPlayer > 0.0D) {
            final int i = (int) (lightPlayer * 16.0D);
            final int j = combinedLight & 255;
            if (i > j) {
                combinedLight = combinedLight & -256;
                combinedLight = combinedLight | i;
            }
        }
        return combinedLight;
    }

    public static double getLightLevel(final BlockPos pos) {
        double d0 = 0.0D;
        synchronized (mapDynamicLights) {
            final List<DynamicLight> list = mapDynamicLights.valueList();
            for (final DynamicLight dynamiclight : list) {
                int k = dynamiclight.getLastLightLevel();
                if (k > 0) {
                    final double d1 = dynamiclight.getLastPosX();
                    final double d2 = dynamiclight.getLastPosY();
                    final double d3 = dynamiclight.getLastPosZ();
                    final double d4 = pos.getX() - d1;
                    final double d5 = pos.getY() - d2;
                    final double d6 = pos.getZ() - d3;
                    double d7 = d4 * d4 + d5 * d5 + d6 * d6;
                    if (dynamiclight.isUnderwater() && !Config.isClearWater()) {
                        k = Config.limit(k - 2, 0, 15);
                        d7 *= 2.0D;
                    }
                    if (d7 <= 56.25D) {
                        final double d8 = MeowMath.sqrt(d7);
                        final double d9 = 1.0D - d8 / 7.5D;
                        final double d10 = d9 * k;
                        if (d10 > d0) {
                            d0 = d10;
                        }
                    }
                }
            }
        }
        return Config.limit(d0, 0.0D, 15.0D);
    }

    public static int getLightLevel(final ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        } else {
            final Item item = itemStack.getItem();
            if (item instanceof ItemBlock) {
                ItemBlock itemblock = (ItemBlock) item;
                final Block block = itemblock.getBlock();
                if (block != null) {
                    return block.getLightValue();
                }
            }
            if (item == Items.lava_bucket) {
                return Blocks.lava.getLightValue();
            } else if (item != Items.blaze_rod && item != Items.blaze_powder) {
                if ((item == Items.glowstone_dust) || (item == Items.prismarine_crystals) || (item == Items.magma_cream)) {
                    return 8;
                } else if (item == Items.nether_star) {
                    return Blocks.beacon.getLightValue() / 2;
                } else {
                    if (!mapItemLightLevels.isEmpty()) {
                        final Integer integer = mapItemLightLevels.get(item);
                        if (integer != null) {
                            return integer;
                        }
                    }
                    return 0;
                }
            } else {
                return 10;
            }
        }
    }

    public static int getLightLevel(final Entity entity) {
        if (entity == Config.getMinecraft().getRenderViewEntity() && !Config.isDynamicHandLight()) {
            return 0;
        } else {
            if (entity instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) entity;
                if (entityplayer.isSpectator()) {
                    return 0;
                }
            }
            if (entity.isBurning()) {
                return 15;
            } else {
                if (!mapEntityLightLevels.isEmpty()) {
                    final Integer integer = mapEntityLightLevels.get(entity.getClass());
                    if (integer != null) {
                        return integer;
                    }
                }
                if ((entity instanceof EntityFireball) || (entity instanceof EntityTNTPrimed)) {
                    return 15;
                } else if (entity instanceof EntityBlaze) {
                    EntityBlaze entityblaze = (EntityBlaze) entity;
                    return entityblaze.func_70845_n() ? 15 : 10;
                } else if (entity instanceof EntityMagmaCube) {
                    EntityMagmaCube entitymagmacube = (EntityMagmaCube) entity;
                    return entitymagmacube.squishFactor > 0.6D ? 13 : 8;
                } else {
                    if (entity instanceof EntityCreeper) {
                        EntityCreeper entitycreeper = (EntityCreeper) entity;
                        if (entitycreeper.getCreeperFlashIntensity(0.0F) > 0.001D) {
                            return 15;
                        }
                    }
                    if (entity instanceof EntityLivingBase) {
                        EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
                        final ItemStack itemstack2 = entitylivingbase.getHeldItem();
                        final int i = getLightLevel(itemstack2);
                        final ItemStack itemstack1 = entitylivingbase.getEquipmentInSlot(4);
                        final int j = getLightLevel(itemstack1);
                        return Math.max(i, j);
                    } else if (entity instanceof EntityItem) {
                        EntityItem entityitem = (EntityItem) entity;
                        final ItemStack itemstack = getItemStack(entityitem);
                        return getLightLevel(itemstack);
                    } else {
                        return 0;
                    }
                }
            }
        }
    }

    public static void removeLights(final RenderGlobal renderGlobal) {
        synchronized (mapDynamicLights) {
            final List<DynamicLight> list = mapDynamicLights.valueList();
            for (final DynamicLight dynamiclight : list) {
                dynamiclight.updateLitChunks(renderGlobal);
            }
            mapDynamicLights.clear();
        }
    }

    public static void clear() {
        synchronized (mapDynamicLights) {
            mapDynamicLights.clear();
        }
    }

    public static int getCount() {
        synchronized (mapDynamicLights) {
            return mapDynamicLights.size();
        }
    }

    public static ItemStack getItemStack(final EntityItem entityItem) {
        return entityItem.getDataWatcher().getWatchableObjectItemStack(10);
    }

    @SuppressWarnings("unused")
    public static void entityAdded(Entity entityIn, RenderGlobal renderGlobal) {
    }
}
