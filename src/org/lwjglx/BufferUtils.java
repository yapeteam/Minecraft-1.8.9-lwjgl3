package org.lwjglx;

import org.lwjgl.PointerBuffer;

import java.nio.*;

public final class BufferUtils {

    /**
     * Construct a direct native-ordered bytebuffer with the specified size.
     * @param size The size, in bytes
     * @return a ByteBuffer
     */
    public static ByteBuffer createByteBuffer(int size) {
        return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
    }

    /**
     * Construct a direct native-order shortbuffer with the specified number
     * of elements.
     * @param size The size, in shorts
     * @return a ShortBuffer
     */
    public static ShortBuffer createShortBuffer(int size) {
        return createByteBuffer(size << 1).asShortBuffer();
    }

    /**
     * Construct a direct native-order charbuffer with the specified number
     * of elements.
     * @param size The size, in chars
     * @return an CharBuffer
     */
    public static CharBuffer createCharBuffer(int size) {
        return createByteBuffer(size << 1).asCharBuffer();
    }

    /**
     * Construct a direct native-order intbuffer with the specified number
     * of elements.
     * @param size The size, in ints
     * @return an IntBuffer
     */
    public static IntBuffer createIntBuffer(int size) {
        return createByteBuffer(size << 2).asIntBuffer();
    }

    /**
     * Construct a direct native-order longbuffer with the specified number
     * of elements.
     * @param size The size, in longs
     * @return an LongBuffer
     */
    public static LongBuffer createLongBuffer(int size) {
        return createByteBuffer(size << 3).asLongBuffer();
    }

    /**
     * Construct a direct native-order floatbuffer with the specified number
     * of elements.
     * @param size The size, in floats
     * @return a FloatBuffer
     */
    public static FloatBuffer createFloatBuffer(int size) {
        return createByteBuffer(size << 2).asFloatBuffer();
    }

    /**
     * Construct a direct native-order doublebuffer with the specified number
     * of elements.
     * @param size The size, in floats
     * @return a FloatBuffer
     */
    public static DoubleBuffer createDoubleBuffer(int size) {
        return createByteBuffer(size << 3).asDoubleBuffer();
    }

    /**
     * Construct a PointerBuffer with the specified number
     * of elements.
     * @param size The size, in memory addresses
     * @return a PointerBuffer
     */
    public static PointerBuffer createPointerBuffer(int size) {
        return PointerBuffer.allocateDirect(size);
    }

    /**
     * @return n, where buffer_element_size=2^n.
     */
    public static int getElementSizeExponent(Buffer buf) {
        if (buf instanceof ByteBuffer)
            return 0;
        else if (buf instanceof ShortBuffer || buf instanceof CharBuffer)
            return 1;
        else if (buf instanceof FloatBuffer || buf instanceof IntBuffer)
            return 2;
        else if (buf instanceof LongBuffer || buf instanceof DoubleBuffer)
            return 3;
        else
            throw new IllegalStateException("Unsupported buffer type: " + buf);
    }

    /**
     * A helper function which is used to get the byte offset in an arbitrary buffer
     * based on its position
     * @return the position of the buffer, in BYTES
     */
    public static int getOffset(Buffer buffer) {
        return buffer.position() << getElementSizeExponent(buffer);
    }

    /** Fill buffer with zeros from position to remaining */
    public static void zeroBuffer(ByteBuffer b) {
        zeroBuffer0(b, b.position(), b.remaining());
    }

    /** Fill buffer with zeros from position to remaining */
    public static void zeroBuffer(ShortBuffer b) {
        zeroBuffer0(b, b.position()*2L, b.remaining()*2L);
    }

    /** Fill buffer with zeros from position to remaining */
    public static void zeroBuffer(CharBuffer b) {
        zeroBuffer0(b, b.position()*2L, b.remaining()*2L);
    }

    /** Fill buffer with zeros from position to remaining */
    public static void zeroBuffer(IntBuffer b) {
        zeroBuffer0(b, b.position()*4L, b.remaining()*4L);
    }

    /** Fill buffer with zeros from position to remaining */
    public static void zeroBuffer(FloatBuffer b) {
        zeroBuffer0(b, b.position()*4L, b.remaining()*4L);
    }

    /** Fill buffer with zeros from position to remaining */
    public static void zeroBuffer(LongBuffer b) {
        zeroBuffer0(b, b.position()*8L, b.remaining()*8L);
    }

    /** Fill buffer with zeros from position to remaining */
    public static void zeroBuffer(DoubleBuffer b) {
        zeroBuffer0(b, b.position()*8L, b.remaining()*8L);
    }

    /** Fill buffer with zeros from position to remaining */
    private static native void zeroBuffer0(Buffer b, long off, long size);

    /**
     * Returns the memory address of the specified buffer.
     *
     * @param buffer the buffer
     *
     * @return the memory address
     */
    static native long getBufferAddress(Buffer buffer);

}
