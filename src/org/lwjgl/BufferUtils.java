package org.lwjgl;

import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryUtil;

import java.nio.*;
import java.nio.ByteOrder;

/**
 * LWJGL2 shim for org.lwjgl.BufferUtils.
 *
 * This class REPLACES org.lwjgl.BufferUtils from the LWJGL3 jar on the classpath.
 * It must therefore re-implement ALL methods that LWJGL3 internal code calls,
 * including the package-private getAllocationSize() used by PointerBuffer and
 * CLongBuffer.allocateDirect().
 */
public final class BufferUtils {

    /**
     * Package-private helper called by PointerBuffer.allocateDirect() and
     * CLongBuffer.allocateDirect(). Computes byte capacity from element count
     * and element size expressed as a bit-shift (e.g. POINTER_SHIFT=3 → 8 bytes).
     */
    static int getAllocationSize(int elements, int elementShift) {
        return elements << elementShift;
    }

    public static ByteBuffer createByteBuffer(int capacity) {
        return ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());
    }

    public static ShortBuffer createShortBuffer(int capacity) {
        return createByteBuffer(capacity << 1).asShortBuffer();
    }

    public static CharBuffer createCharBuffer(int capacity) {
        return createByteBuffer(capacity << 1).asCharBuffer();
    }

    public static IntBuffer createIntBuffer(int capacity) {
        return createByteBuffer(capacity << 2).asIntBuffer();
    }

    public static LongBuffer createLongBuffer(int capacity) {
        return createByteBuffer(capacity << 3).asLongBuffer();
    }

    public static CLongBuffer createCLongBuffer(int capacity) {
        return CLongBuffer.allocateDirect(capacity);
    }

    public static FloatBuffer createFloatBuffer(int capacity) {
        return createByteBuffer(capacity << 2).asFloatBuffer();
    }

    public static DoubleBuffer createDoubleBuffer(int capacity) {
        return createByteBuffer(capacity << 3).asDoubleBuffer();
    }

    public static PointerBuffer createPointerBuffer(int capacity) {
        return PointerBuffer.allocateDirect(capacity);
    }

    // --- zeroBuffer: LWJGL2 API + LWJGL3.3.2 API ---

    public static void zeroBuffer(ByteBuffer buffer) {
        MemoryUtil.memSet(buffer, 0);
    }

    public static void zeroBuffer(ShortBuffer buffer) {
        MemoryUtil.memSet(buffer, 0);
    }

    public static void zeroBuffer(CharBuffer buffer) {
        MemoryUtil.memSet(buffer, 0);
    }

    public static void zeroBuffer(IntBuffer buffer) {
        MemoryUtil.memSet(buffer, 0);
    }

    public static void zeroBuffer(FloatBuffer buffer) {
        MemoryUtil.memSet(buffer, 0);
    }

    public static void zeroBuffer(LongBuffer buffer) {
        MemoryUtil.memSet(buffer, 0);
    }

    public static void zeroBuffer(DoubleBuffer buffer) {
        MemoryUtil.memSet(buffer, 0);
    }

    public static <T extends CustomBuffer<T>> void zeroBuffer(T buffer) {
        MemoryUtil.memSet(buffer, 0);
    }
}
