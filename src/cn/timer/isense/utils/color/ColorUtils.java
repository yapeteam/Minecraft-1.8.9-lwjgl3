package cn.timer.isense.utils.color;

import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class ColorUtils {
    public static void glColor(int hex) {
        float alpha = (hex >> 24 & 0xFF) / 255.0F;
        float red = (hex >> 16 & 0xFF) / 255.0F;
        float green = (hex >> 8 & 0xFF) / 255.0F;
        float blue = (hex & 0xFF) / 255.0F;
        GlStateManager.color(red, green, blue, alpha);
    }

    public static Color getIColor(int color) {
        return new Color((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF);
    }

    public static int reAlpha(final int color, final float alpha) {
        try {
            final Color c = new Color(color);
            final float r = 0.003921569f * c.getRed();
            final float g = 0.003921569f * c.getGreen();
            final float b = 0.003921569f * c.getBlue();
            return new Color(r, g, b, alpha).getRGB();
        } catch (Throwable e) {
            return color;
        }
    }

    public static Color getColorFromHex(int color) {
        return getIColor(color);
    }

    public static int randomColor() {
        return 0xFF000000 | (int) (Math.random() * 1.6777215E7);
    }

    public static int colorCode(final String substring, final int alpha) {
        switch (substring.toLowerCase()) {
            case "0": {
                return new Color(0, 0, 0, alpha).getRGB();
            }
            case "1": {
                return new Color(0, 0, 170, alpha).getRGB();
            }
            case "2": {
                return new Color(0, 170, 0, alpha).getRGB();
            }
            case "3": {
                return new Color(0, 170, 170, alpha).getRGB();
            }
            case "4": {
                return new Color(170, 0, 0, alpha).getRGB();
            }
            case "5": {
                return new Color(170, 0, 170, alpha).getRGB();
            }
            case "6": {
                return new Color(255, 170, 0, alpha).getRGB();
            }
            case "7": {
                return new Color(170, 170, 170, alpha).getRGB();
            }
            case "8": {
                return new Color(85, 85, 85, alpha).getRGB();
            }
            case "9": {
                return new Color(85, 85, 255, alpha).getRGB();
            }
            case "a": {
                return new Color(85, 255, 85, alpha).getRGB();
            }
            case "b": {
                return new Color(85, 255, 255, alpha).getRGB();
            }
            case "c": {
                return new Color(255, 85, 85, alpha).getRGB();
            }
            case "d": {
                return new Color(255, 85, 255, alpha).getRGB();
            }
            case "e": {
                return new Color(255, 255, 85, alpha).getRGB();
            }
            default: {
                return new Color(255, 255, 255, alpha).getRGB();
            }
        }
    }

    public static int darker(final int color, final float factor) {
        final int r = (int) ((color >> 16 & 0xFF) * factor);
        final int g = (int) ((color >> 8 & 0xFF) * factor);
        final int b = (int) ((color & 0xFF) * factor);
        final int a = color >> 24 & 0xFF;
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF) | (a & 0xFF) << 24;
    }

    public static int transparency(final int color, final double alpha) {
        final Color c = new Color(color);
        final float r = 0.003921569f * c.getRed();
        final float g = 0.003921569f * c.getGreen();
        final float b = 0.003921569f * c.getBlue();
        return new Color(r, g, b, (float) alpha).getRGB();
    }

    public static int transparency(final Color color, final double alpha) {
        return new Color((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue(), (float) alpha).getRGB();
    }

    public static Color rainbow(final long offset) {
        final float hue = (System.nanoTime() + offset) / 1.0E10f % 1.0f;
        return new Color(Color.HSBtoRGB(hue, 1.0f, 1.0f));
    }

    public static int fadeBetween(final int startColour, final int endColour, double progress) {
        if (progress > 1.0) {
            progress = 1.0 - progress % 1.0;
        }
        return fadeTo(startColour, endColour, progress);
    }

    public static int fadeBetween(final int startColour, final int endColour, final long offset) {
        return fadeBetween(startColour, endColour, (System.currentTimeMillis() + offset) % 2000L / 1000.0);
    }

    public static int fadeBetween(final int startColour, final int endColour) {
        return fadeBetween(startColour, endColour, 0L);
    }

    public static int fadeTo(final int startColour, final int endColour, final double progress) {
        final double invert = 1.0 - progress;
        final int r = (int) ((startColour >> 16 & 0xFF) * invert + (endColour >> 16 & 0xFF) * progress);
        final int g = (int) ((startColour >> 8 & 0xFF) * invert + (endColour >> 8 & 0xFF) * progress);
        final int b = (int) ((startColour & 0xFF) * invert + (endColour & 0xFF) * progress);
        final int a = (int) ((startColour >> 24 & 0xFF) * invert + (endColour >> 24 & 0xFF) * progress);
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }

    public static float[] getRGBA(final int color) {
        final float a = (color >> 24 & 0xFF) / 255.0f;
        final float r = (color >> 16 & 0xFF) / 255.0f;
        final float g = (color >> 8 & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        return new float[]{r, g, b, a};
    }

    public static int intFromHex(final String hex) {
        try {
            if (hex.equalsIgnoreCase("rainbow")) {
                return rainbow(0L).getRGB();
            }
            return Integer.parseInt(hex, 16);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String hexFromInt(final int color) {
        return hexFromInt(new Color(color));
    }

    public static String hexFromInt(final Color color) {
        return Integer.toHexString(color.getRGB()).substring(2);
    }

    public static Color blend(final Color color1, final Color color2, final double ratio) {
        final float r = (float) ratio;
        final float ir = 1.0f - r;
        final float[] rgb1 = new float[3];
        final float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        return new Color(rgb1[0] * r + rgb2[0] * ir, rgb1[1] * r + rgb2[1] * ir, rgb1[2] * r + rgb2[2] * ir);
    }

    public static Color blend(final Color color1, final Color color2) {
        return blend(color1, color2, 0.5);
    }

    public static Color darker(final Color color, final double fraction) {
        int red = (int) Math.round(color.getRed() * (1.0 - fraction));
        int green = (int) Math.round(color.getGreen() * (1.0 - fraction));
        int blue = (int) Math.round(color.getBlue() * (1.0 - fraction));
        if (red < 0) {
            red = 0;
        } else if (red > 255) {
            red = 255;
        }
        if (green < 0) {
            green = 0;
        } else if (green > 255) {
            green = 255;
        }
        if (blue < 0) {
            blue = 0;
        } else if (blue > 255) {
            blue = 255;
        }
        final int alpha = color.getAlpha();
        return new Color(red, green, blue, alpha);
    }

    public static Color lighter(final Color color, final double fraction) {
        int red = (int) Math.round(color.getRed() * (1.0 + fraction));
        int green = (int) Math.round(color.getGreen() * (1.0 + fraction));
        int blue = (int) Math.round(color.getBlue() * (1.0 + fraction));
        if (red < 0) {
            red = 0;
        } else if (red > 255) {
            red = 255;
        }
        if (green < 0) {
            green = 0;
        } else if (green > 255) {
            green = 255;
        }
        if (blue < 0) {
            blue = 0;
        } else if (blue > 255) {
            blue = 255;
        }
        final int alpha = color.getAlpha();
        return new Color(red, green, blue, alpha);
    }

    public static double colorDistance(final double r1, final double g1, final double b1, final double r2, final double g2, final double b2) {
        final double a = r2 - r1;
        final double b3 = g2 - g1;
        final double c = b2 - b1;
        return Math.sqrt(a * a + b3 * b3 + c * c);
    }

    public static double colorDistance(final double[] color1, final double[] color2) {
        return colorDistance(color1[0], color1[1], color1[2], color2[0], color2[1], color2[2]);
    }

    public static double colorDistance(final Color color1, final Color color2) {
        final float[] rgb1 = new float[3];
        final float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        return colorDistance(rgb1[0], rgb1[1], rgb1[2], rgb2[0], rgb2[1], rgb2[2]);
    }

    public static boolean isDark(final double r, final double g, final double b) {
        final double dWhite = colorDistance(r, g, b, 1.0, 1.0, 1.0);
        final double dBlack = colorDistance(r, g, b, 0.0, 0.0, 0.0);
        return dBlack < dWhite;
    }

    public static boolean isDark(final Color color) {
        final float r = color.getRed() / 255.0f;
        final float g = color.getGreen() / 255.0f;
        final float b = color.getBlue() / 255.0f;
        return isDark(r, g, b);
    }

    public static Color getHealthColor(final float health, final float maxHealth) {
        final float[] fractions = {0.0f, 0.5f, 1.0f};
        final Color[] colors = {new Color(150, 30, 30), new Color(255, 0, 60), Color.GREEN};
        final float progress = health / maxHealth;
        return blendColors(fractions, colors, progress).brighter();
    }

    public static Color blendColors(final float[] fractions, final Color[] colors, final float progress) {
        if (fractions.length == colors.length) {
            final int[] indices = getFractionIndices(fractions, progress);
            final float[] range = {fractions[indices[0]], fractions[indices[1]]};
            final Color[] colorRange = {colors[indices[0]], colors[indices[1]]};
            final float max = range[1] - range[0];
            final float value = progress - range[0];
            final float weight = value / max;
            return blend(colorRange[0], colorRange[1], 1.0f - weight);
        }
        throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
    }

    public static int[] getFractionIndices(final float[] fractions, final float progress) {
        final int[] range = new int[2];
        int startPoint = 0;
        while (startPoint < fractions.length && fractions[startPoint] <= progress) {
            ++startPoint;
        }
        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }
        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
    }

    public static int getColor(final Color color) {
        return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int getColor(final int brightness) {
        return getColor(brightness, brightness, brightness, 255);
    }

    public static int getColor(final int brightness, final int alpha) {
        return getColor(brightness, brightness, brightness, alpha);
    }

    public static int getColor(final int red, final int green, final int blue) {
        return getColor(red, green, blue, 255);
    }

    public static int getColor(final int red, final int green, final int blue, final int alpha) {
        int color = 0;
        color |= alpha << 24;
        color |= red << 16;
        color |= green << 8;
        return color | blue;
    }
}
