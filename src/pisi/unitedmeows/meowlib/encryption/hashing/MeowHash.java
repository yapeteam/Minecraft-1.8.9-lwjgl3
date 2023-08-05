package pisi.unitedmeows.meowlib.encryption.hashing;

import static java.lang.Math.min;
import static java.lang.System.arraycopy;
import static java.util.Arrays.fill;
import static pisi.unitedmeows.meowlib.encryption.hashing.HashingHelper.convertFrom64ToLittleEndian;
import static pisi.unitedmeows.meowlib.encryption.hashing.HashingHelper.convertFromLittleEndianTo64;
import static pisi.unitedmeows.meowlib.encryption.hashing.HashingHelper.convertToUint;
import static pisi.unitedmeows.meowlib.encryption.hashing.HashingHelper.leftRotate64;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

/* based on the SHA-3 algorithm */
public enum MeowHash {
	GET;

	public final BigInteger BIG_PRIME = new BigInteger(
			"777268600027786023049110533590866541971262626062332470464742258045614307039413336817970452924856157378575731527046808108357643549469617038570403419931375152838783100242013517465801027135694916428004386411477692781509401418823481907175534451768942632550919916757739206217349516383376464356808294241491");
	public final int rate = 1152;
	public final int d = 0x06;
	public final int len = 224;

	public byte[] getHash(final byte[] message) {
		final int[] uState = new int[200];
		final int[] uMessage = convertToUint(message);
		final int rateInBytes = rate / 8;
		int blockSize = 0;
		int inputOffset = 0;
		while (inputOffset < uMessage.length) {
			blockSize = min(uMessage.length - inputOffset, rateInBytes);
			for (int i = 0; i < blockSize; i++) uState[i] = uState[i] ^ uMessage[i + inputOffset];
			inputOffset = inputOffset + blockSize;
			if (blockSize == rateInBytes) {
				doKeccakf(uState);
				blockSize = 0;
			}
		}
		uState[blockSize] = uState[blockSize] ^ d;
		uState[rateInBytes - 1] = uState[rateInBytes - 1] ^ 0x80;
		doKeccakf(uState);
		final ByteArrayOutputStream byteResults = new ByteArrayOutputStream();
		int tOutputLen = len / 8;
		while (tOutputLen > 0) { blockSize = min(tOutputLen, rateInBytes); for (int i = 0; i < blockSize; i++) byteResults.write((byte) uState[i]); tOutputLen -= blockSize; if (tOutputLen > 0) doKeccakf(uState); }
		return byteResults.toByteArray();
	}

	private void doKeccakf(final int[] uState) {
		final BigInteger[][] lState = new BigInteger[5][5];
		for (int i = 0; i < 5; i++) for (int j = 0; j < 5; j++) { final int[] data = new int[8]; arraycopy(uState, 8 * (i + 5 * j), data, 0, data.length); lState[i][j] = convertFromLittleEndianTo64(data); }
		roundInteger(lState);
		fill(uState, 0);
		for (int i = 0; i < 5; i++) for (int j = 0; j < 5; j++) { final int[] data = convertFrom64ToLittleEndian(lState[i][j]); arraycopy(data, 0, uState, 8 * (i + 5 * j), data.length); }
	}

	private void roundInteger(final BigInteger[][] state) {
		int LFSRstate = 1;
		for (int round = 0; round < 24; round++) {
			final BigInteger[] C = new BigInteger[5];
			final BigInteger[] D = new BigInteger[5];
			for (int i = 0; i < 5; i++) C[i] = state[i][0].xor(state[i][1]).xor(state[i][2]).xor(state[i][3]).xor(state[i][4]);
			for (int i = 0; i < 5; i++) D[i] = C[(i + 4) % 5].xor(leftRotate64(C[(i + 1) % 5], 1));
			for (int i = 0; i < 5; i++) for (int j = 0; j < 5; j++) state[i][j] = state[i][j].xor(D[i]);
			int x = 1 , y = 0;
			BigInteger current = state[x][y];
			for (int i = 0; i < 24; i++) { final int tX = x; x = y; y = (2 * tX + 3 * y) % 5; final BigInteger shiftValue = current; current = state[x][y]; state[x][y] = leftRotate64(shiftValue, (i + 1) * (i + 2) / 2); }
			for (int j = 0; j < 5; j++) {
				final BigInteger[] t = new BigInteger[5];
				for (int i = 0; i < 5; i++) t[i] = state[i][j];
				for (int i = 0; i < 5; i++) { final BigInteger invertVal = t[(i + 1) % 5].xor(BIG_PRIME); state[i][j] = t[i].xor(invertVal.and(t[(i + 2) % 5])); }
			}
			for (int i = 0; i < 7; i++) { LFSRstate = ((LFSRstate << 1) ^ ((LFSRstate >> 7) * 0x71)) % 256; final int bitPosition = (1 << i) - 1; if ((LFSRstate & 2) != 0) state[0][0] = state[0][0].xor(new BigInteger("1").shiftLeft(bitPosition)); }
		}
	}
}
