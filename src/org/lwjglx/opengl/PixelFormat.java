package org.lwjglx.opengl;

public class PixelFormat {

    /**
     * We probybly dont even need this class but so it is drop in place
     */

    /**
     * The number of bits per pixel, exluding alpha.
     * This parameter is ignored in Display.create().
     */
    private int bpp;
    /** The number of alpha bits. */
    private int alpha;
    /** The number of depth buffer bits */
    private int depth;
    /** The number of stencil bits */
    private int stencil;
    /**
     * The number of samples to use in anti-aliasing.
     * 0 means that anti-aliasing is disabled.
     */
    private int samples;
    /**
     * The number of COLOR_SAMPLES_NV to use for Coverage Sample Anti-aliasing (CSAA).
     * When this number is greater than 0, the {@code samples} property will be treated
     * as if it were the COVERAGE_SAMPLES_NV property.
     * <p/>
     * This property is currently a no-op for the MacOS implementation.
     */
    private int colorSamples;
    /** The number of auxiliary buffers */
    private int num_aux_buffers;
    /** The number of bits per pixel in the accumulation buffer */
    private int accum_bpp;
    /** The number of alpha bits in the accumulation buffer */
    private int accum_alpha;
    /** Whether this format requires a stereo buffer */
    private boolean stereo;
    /** Whether this format specifies a floating point format */
    private boolean floating_point;
    /**
     * Whether this format specifies a packed floating point format (32 bit unsigned - R11F_G11F_B10F)
     * This property is currently a no-op for the MacOS implementation.
     */
    private boolean floating_point_packed;
    /**
     * Whether this format specifies an sRGB format
     * This property is currently a no-op for the MacOS implementation.
     */
    private boolean sRGB;

    /**
     * Default pixel format is minimum 8 bits depth, and no alpha
     * nor stencil requirements.
     */
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
        this(bpp, alpha, depth, stencil, samples, 0, 0, 0, false);
    }

    public PixelFormat(int bpp, int alpha, int depth, int stencil, int samples, int num_aux_buffers, int accum_bpp, int accum_alpha, boolean stereo) {
        this(bpp, alpha, depth, stencil, samples, num_aux_buffers, accum_bpp, accum_alpha, stereo, false);
    }

    public PixelFormat(int bpp, int alpha, int depth, int stencil, int samples, int num_aux_buffers, int accum_bpp, int accum_alpha, boolean stereo, boolean floating_point) {
        this.bpp = bpp;
        this.alpha = alpha;
        this.depth = depth;
        this.stencil = stencil;

        this.samples = samples;

        this.num_aux_buffers = num_aux_buffers;

        this.accum_bpp = accum_bpp;
        this.accum_alpha = accum_alpha;

        this.stereo = stereo;

        this.floating_point = floating_point;
        this.floating_point_packed = false;
        this.sRGB = false;
    }

    private PixelFormat(final PixelFormat pf) {
        this.bpp = pf.bpp;
        this.alpha = pf.alpha;
        this.depth = pf.depth;
        this.stencil = pf.stencil;

        this.samples = pf.samples;
        this.colorSamples = pf.colorSamples;

        this.num_aux_buffers = pf.num_aux_buffers;

        this.accum_bpp = pf.accum_bpp;
        this.accum_alpha = pf.accum_alpha;

        this.stereo = pf.stereo;

        this.floating_point = pf.floating_point;
        this.floating_point_packed = pf.floating_point_packed;
        this.sRGB = pf.sRGB;
    }

    public int getBitsPerPixel() {
        return bpp;
    }

    /**
     * Returns a new PixelFormat object with the same properties as this PixelFormat and the new bits per pixel value.
     *
     * @param bpp the new bits per pixel value.
     *
     * @return the new PixelFormat
     */
    public PixelFormat withBitsPerPixel(final int bpp) {
        if ( bpp < 0 )
            throw new IllegalArgumentException("Invalid number of bits per pixel specified: " + bpp);

        final PixelFormat pf = new PixelFormat(this);
        pf.bpp = bpp;
        return pf;
    }

    public int getAlphaBits() {
        return alpha;
    }

    /**
     * Returns a new PixelFormat object with the same properties as this PixelFormat and the new alpha bits value.
     *
     * @param alpha the new alpha bits value.
     *
     * @return the new PixelFormat
     */
    public PixelFormat withAlphaBits(final int alpha) {
        if ( alpha < 0 )
            throw new IllegalArgumentException("Invalid number of alpha bits specified: " + alpha);

        final PixelFormat pf = new PixelFormat(this);
        pf.alpha = alpha;
        return pf;
    }

    public int getDepthBits() {
        return depth;
    }

    /**
     * Returns a new PixelFormat object with the same properties as this PixelFormat and the new depth bits value.
     *
     * @param depth the new depth bits value.
     *
     * @return the new PixelFormat
     */
    public PixelFormat withDepthBits(final int depth) {
        if ( depth < 0 )
            throw new IllegalArgumentException("Invalid number of depth bits specified: " + depth);

        final PixelFormat pf = new PixelFormat(this);
        pf.depth = depth;
        return pf;
    }

    public int getStencilBits() {
        return stencil;
    }

    /**
     * Returns a new PixelFormat object with the same properties as this PixelFormat and the new stencil bits value.
     *
     * @param stencil the new stencil bits value.
     *
     * @return the new PixelFormat
     */
    public PixelFormat withStencilBits(final int stencil) {
        if ( stencil < 0 )
            throw new IllegalArgumentException("Invalid number of stencil bits specified: " + stencil);

        final PixelFormat pf = new PixelFormat(this);
        pf.stencil = stencil;
        return pf;
    }

    public int getSamples() {
        return samples;
    }

    /**
     * Returns a new PixelFormat object with the same properties as this PixelFormat and the new samples value.
     *
     * @param samples the new samples value.
     *
     * @return the new PixelFormat
     */
    public PixelFormat withSamples(final int samples) {
        if ( samples < 0 )
            throw new IllegalArgumentException("Invalid number of samples specified: " + samples);

        final PixelFormat pf = new PixelFormat(this);
        pf.samples = samples;
        return pf;
    }

    /**
     * Returns a new PixelFormat object with the same properties as this PixelFormat and the new color samples values.
     * A value greater than 0 is valid only if the {@code samples} property is also greater than 0. Additionally, the
     * color samples value needs to be lower than or equal to the {@code samples} property.
     *
     * @param colorSamples    the new color samples value.
     *
     * @return the new PixelFormat
     */
    public PixelFormat withCoverageSamples(final int colorSamples) {
        return withCoverageSamples(colorSamples, samples);
    }

    /**
     * Returns a new PixelFormat object with the same properties as this PixelFormat and the new color samples
     * and coverage samples values.
     *
     * @param colorSamples    the new color samples value. This value must be lower than or equal to the coverage samples value.
     * @param coverageSamples the new coverage samples value.
     *
     * @return the new PixelFormat
     */
    public PixelFormat withCoverageSamples(final int colorSamples, final int coverageSamples) {
        if ( coverageSamples < 0 || colorSamples < 0 || (coverageSamples == 0 && 0 < colorSamples) || coverageSamples < colorSamples  )
            throw new IllegalArgumentException("Invalid number of coverage samples specified: " + coverageSamples + " - " + colorSamples);

        final PixelFormat pf = new PixelFormat(this);
        pf.samples = coverageSamples;
        pf.colorSamples = colorSamples;
        return pf;
    }

    public int getAuxBuffers() {
        return num_aux_buffers;
    }

    /**
     * Returns a new PixelFormat object with the same properties as this PixelFormat and the new auxiliary buffers value.
     *
     * @param num_aux_buffers the new auxiliary buffers value.
     *
     * @return the new PixelFormat
     */
    public PixelFormat withAuxBuffers(final int num_aux_buffers) {
        if ( num_aux_buffers < 0 )
            throw new IllegalArgumentException("Invalid number of auxiliary buffers specified: " + num_aux_buffers);

        final PixelFormat pf = new PixelFormat(this);
        pf.num_aux_buffers = num_aux_buffers;
        return pf;
    }

    public int getAccumulationBitsPerPixel() {
        return accum_bpp;
    }

    /**
     * Returns a new PixelFormat object with the same properties as this PixelFormat and the new bits per pixel in the accumulation buffer value.
     *
     * @param accum_bpp the new bits per pixel in the accumulation buffer value.
     *
     * @return the new PixelFormat
     */
    public PixelFormat withAccumulationBitsPerPixel(final int accum_bpp) {
        if ( accum_bpp < 0 )
            throw new IllegalArgumentException("Invalid number of bits per pixel in the accumulation buffer specified: " + accum_bpp);

        final PixelFormat pf = new PixelFormat(this);
        pf.accum_bpp = accum_bpp;
        return pf;
    }

    public int getAccumulationAlpha() {
        return accum_alpha;
    }

    /**
     * Returns a new PixelFormat object with the same properties as this PixelFormat and the new alpha bits in the accumulation buffer value.
     *
     * @param accum_alpha the new alpha bits in the accumulation buffer value.
     *
     * @return the new PixelFormat
     */
    public PixelFormat withAccumulationAlpha(final int accum_alpha) {
        if ( accum_alpha < 0 )
            throw new IllegalArgumentException("Invalid number of alpha bits in the accumulation buffer specified: " + accum_alpha);

        final PixelFormat pf = new PixelFormat(this);
        pf.accum_alpha = accum_alpha;
        return pf;
    }

    public boolean isStereo() {
        return stereo;
    }

    /**
     * Returns a new PixelFormat object with the same properties as this PixelFormat and the new stereo value.
     *
     * @param stereo the new stereo value.
     *
     * @return the new PixelFormat
     */
    public PixelFormat withStereo(final boolean stereo) {
        final PixelFormat pf = new PixelFormat(this);
        pf.stereo = stereo;
        return pf;
    }

    public boolean isFloatingPoint() {
        return floating_point;
    }

    /**
     * Returns a new PixelFormat object with the same properties as this PixelFormat and the new floating point value.
     * If floating_point is true, floating_point_packed will be reset to false.
     *
     * @param floating_point the new floating point value.
     *
     * @return the new PixelFormat
     */
    public PixelFormat withFloatingPoint(final boolean floating_point) {
        final PixelFormat pf = new PixelFormat(this);
        pf.floating_point = floating_point;
        if ( floating_point )
            pf.floating_point_packed = false;
        return pf;
    }

    /**
     * Returns a new PixelFormat object with the same properties as this PixelFormat and the new packed floating point value.
     * If floating_point_packed is true, floating_point will be reset to false.
     *
     * @param floating_point_packed the new packed floating point value.
     *
     * @return the new PixelFormat
     */
    public PixelFormat withFloatingPointPacked(final boolean floating_point_packed) {
        final PixelFormat pf = new PixelFormat(this);
        pf.floating_point_packed = floating_point_packed;
        if ( floating_point_packed )
            pf.floating_point = false;
        return pf;
    }

    public boolean isSRGB() {
        return sRGB;
    }

    /**
     * Returns a new PixelFormat object with the same properties as this PixelFormat and the new sRGB value.
     *
     * @param sRGB the new floating point value.
     *
     * @return the new PixelFormat
     */
    public PixelFormat withSRGB(final boolean sRGB) {
        final PixelFormat pf = new PixelFormat(this);
        pf.sRGB = sRGB;
        return pf;
    }

}
