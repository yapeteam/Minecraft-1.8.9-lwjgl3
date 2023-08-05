package pisi.unitedmeows.meowlib.encryption.hashing;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

/* from google/guava */
public class HashingHelper {
	private static final byte[] ENCODE_BYTE_TABLE = { (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f' };

	public static int[] convertToUint(final byte[] data) {
		final int[] converted = new int[data.length];
		for (int i = 0; i < data.length; i++) converted[i] = data[i] & 0xFF;
		return converted;
	}

	public static BigInteger convertFromLittleEndianTo64(final int[] data) {
		BigInteger uLong = new BigInteger("0");
		for (int i = 0; i < 8; i++) uLong = uLong.add(new BigInteger(Integer.toString(data[i])).shiftLeft(8 * i));
		return uLong;
	}

	public static int[] convertFrom64ToLittleEndian(final BigInteger uLong) {
		final int[] data = new int[8];
		final BigInteger mod256 = new BigInteger("256");
		for (int i = 0; i < 8; i++) data[i] = uLong.shiftRight((8 * i)).mod(mod256).intValue();
		return data;
	}

	public static BigInteger leftRotate64(final BigInteger value, final int rotate) {
		final BigInteger lp = value.shiftRight(64 - (rotate % 64));
		final BigInteger rp = value.shiftLeft(rotate % 64);
		return lp.add(rp).mod(MeowHash.GET.BIG_PRIME);
	}

	public static String convertBytesToString(final byte[] data) {
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		for (int i = 0; i < data.length; i++) { final int uVal = data[i] & 0xFF; buffer.write(ENCODE_BYTE_TABLE[(uVal >>> 4)]); buffer.write(ENCODE_BYTE_TABLE[uVal & 0xF]); }
		return new String(buffer.toByteArray());
	}
}
