package org.lwjgl.opengl;

public class PixelFormat {

    private int bpp, alpha, depth, stencil, samples;

    public PixelFormat() {
        this(0, 8, 0);
    }

    public PixelFormat(int alpha, int depth, int stencil) {
        this(alpha, depth, stencil, 0);
    }

    public PixelFormat(int alpha, int depth, int stencil, int samples) {
        this(0, alpha, depth, stencil, samples);
    }

    public PixelFormat(int bpp, int alpha, int depth, int stencil, int samples) {
        this.bpp = bpp;
        this.alpha = alpha;
        this.depth = depth;
        this.stencil = stencil;
        this.samples = samples;
    }

    public int getBitsPerPixel() { return bpp; }
    public int getAlphaBits() { return alpha; }
    public int getDepthBits() { return depth; }
    public int getStencilBits() { return stencil; }
    public int getSamples() { return samples; }

    /** Converts to the lwjglx equivalent for internal use by Display. */
    org.lwjglx.opengl.PixelFormat toLwjglx() {
        return new org.lwjglx.opengl.PixelFormat(bpp, alpha, depth, stencil, samples);
    }
}
