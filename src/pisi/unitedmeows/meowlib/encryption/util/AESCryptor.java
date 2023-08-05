package pisi.unitedmeows.meowlib.encryption.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import pisi.unitedmeows.meowlib.encryption.exceptions.BadVersionException;

/**
 * @author Thomas Richner, edited by ghost2173
 * 
 * @apiNote private byte array PBKDF2_SALT could be randomized, probably.
 */
public class AESCryptor {
   private static final byte VERSION_BYTE = 0x01;
   private static final int VERSION_BYTE_LENGTH = 1;
   private static final int AES_KEY_BITS_LENGTH = 128;
   private static final int GCM_IV_BYTES_LENGTH = 12;
   private static final int GCM_TAG_BYTES_LENGTH = 16;
   private static final int PBKDF2_ITERATIONS = 1024;
   public static byte[] PBKDF2_SALT = hexStringToByteArray("4d3fe0d71d2abd2828e7a3196ea450d4");

   /**
    * Decrypts an AES-GCM encrypted ciphertext and is the reverse operation of {@link AESCryptor#encrypt(char[], byte[])}
    *
    * @param password   passphrase for decryption
    * @param ciphertext encrypted bytes
    *
    * @return plaintext bytes
    *
    * @throws NoSuchPaddingException
    * @throws NoSuchAlgorithmException
    * @throws NoSuchProviderException
    * @throws InvalidKeySpecException
    * @throws InvalidAlgorithmParameterException
    * @throws InvalidKeyException
    * @throws BadPaddingException
    * @throws IllegalBlockSizeException
    * @throws IllegalArgumentException
    */
   public byte[] decrypt(char[] password, byte[] ciphertext) throws NoSuchPaddingException,NoSuchAlgorithmException,
		 NoSuchProviderException,InvalidKeySpecException,InvalidAlgorithmParameterException,InvalidKeyException,
		 BadPaddingException,IllegalBlockSizeException,BadVersionException {
	  // input validation
	  if (ciphertext == null) {
		 throw new IllegalArgumentException("Ciphertext cannot be null.");
	  }
	  if (ciphertext.length <= VERSION_BYTE_LENGTH + GCM_IV_BYTES_LENGTH + GCM_TAG_BYTES_LENGTH) {
		 throw new IllegalArgumentException("Ciphertext too short.");
	  }
	  // the version must match, we don't decrypt other versions
	  if (ciphertext[0] != VERSION_BYTE) {
		 throw new BadVersionException();
	  }
	  // input seems legit, lets decrypt and check integrity
	  // derive key from password
	  SecretKey key = deriveAesKey(password, PBKDF2_SALT, AES_KEY_BITS_LENGTH);
	  // init cipher
	  Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "SunJCE");
	  GCMParameterSpec params = new GCMParameterSpec(GCM_TAG_BYTES_LENGTH * 8, ciphertext, VERSION_BYTE_LENGTH,
			GCM_IV_BYTES_LENGTH);
	  cipher.init(Cipher.DECRYPT_MODE, key, params);
	  final int ciphertextOffset = VERSION_BYTE_LENGTH + GCM_IV_BYTES_LENGTH;
	  // add version and IV to MAC
	  cipher.updateAAD(ciphertext, 0, ciphertextOffset);
	  // decipher and check MAC
	  return cipher.doFinal(ciphertext, ciphertextOffset, ciphertext.length - ciphertextOffset);
   }

   /**
    * Encrypts a plaintext with a password.
    *
    * The encryption provides the following security properties: Confidentiality + Integrity
    *
    * This is achieved my using the AES-GCM AEAD blockmode with a randomized IV.
    *
    * The tag is calculated over the version byte, the IV as well as the ciphertext.
    *
    * Finally the encrypted bytes have the following structure:
    * 
    * <pre>
    *          +-------------------------------------------------------------------+
    *          |         |               |                             |           |
    *          | version | IV bytes      | ciphertext bytes            |    tag    |
    *          |         |               |                             |           |
    *          +-------------------------------------------------------------------+
    * Length:     1B        12B            len(plaintext) bytes            16B
    * </pre>
    * 
    * Note: There is no padding required for AES-GCM, but this also implies that the exact plaintext length is revealed.
    *
    * @param password  password to use for encryption
    * @param plaintext plaintext to encrypt
    *
    * @throws NoSuchAlgorithmException
    * @throws NoSuchProviderException
    * @throws NoSuchPaddingException
    * @throws InvalidAlgorithmParameterException
    * @throws InvalidKeyException
    * @throws BadPaddingException
    * @throws IllegalBlockSizeException
    * @throws InvalidKeySpecException
    */
   public byte[] encrypt(char[] password, byte[] plaintext) throws NoSuchAlgorithmException,NoSuchProviderException,
		 NoSuchPaddingException,InvalidAlgorithmParameterException,InvalidKeyException,BadPaddingException,
		 IllegalBlockSizeException,InvalidKeySpecException {
	  // initialise random and generate IV (initialisation vector)
	  SecretKey key = deriveAesKey(password, PBKDF2_SALT, AES_KEY_BITS_LENGTH);
	  final byte[] iv = new byte[GCM_IV_BYTES_LENGTH];
	  SecureRandom random = SecureRandom.getInstanceStrong();
	  random.nextBytes(iv);
	  // encrypt
	  Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "SunJCE");
	  GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_BYTES_LENGTH * 8, iv);
	  cipher.init(Cipher.ENCRYPT_MODE, key, spec);
	  // add IV to MAC
	  final byte[] versionBytes = new byte[] { VERSION_BYTE };
	  cipher.updateAAD(versionBytes);
	  cipher.updateAAD(iv);
	  // encrypt and MAC plaintext
	  byte[] ciphertext = cipher.doFinal(plaintext);
	  // prepend VERSION and IV to ciphertext
	  byte[] encrypted = new byte[1 + GCM_IV_BYTES_LENGTH + ciphertext.length];
	  int pos = 0;
	  System.arraycopy(versionBytes, 0, encrypted, 0, VERSION_BYTE_LENGTH);
	  pos += VERSION_BYTE_LENGTH;
	  System.arraycopy(iv, 0, encrypted, pos, iv.length);
	  pos += iv.length;
	  System.arraycopy(ciphertext, 0, encrypted, pos, ciphertext.length);
	  return encrypted;
   }

   /**
    * We derive a fixed length AES key with uniform entropy from a provided passphrase. This is done with PBKDF2/HMAC256 with a fixed count of iterations and a provided salt.
    *
    * @param password passphrase to derive key from
    * @param salt     salt for PBKDF2 if possible use a per-key salt, alternatively a random constant salt is better than no salt.
    * @param keyLen   number of key bits to output
    *
    * @return a SecretKey for AES derived from a passphrase
    *
    * @throws NoSuchAlgorithmException
    * @throws InvalidKeySpecException
    */
   private SecretKey deriveAesKey(char[] password, byte[] salt, int keyLen)
		 throws NoSuchAlgorithmException,InvalidKeySpecException {
	  if (password == null || salt == null || keyLen <= 0) {
		 throw new IllegalArgumentException();
	  }
	  SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	  KeySpec spec = new PBEKeySpec(password, salt, PBKDF2_ITERATIONS, keyLen);
	  SecretKey pbeKey = factory.generateSecret(spec);
	  return new SecretKeySpec(pbeKey.getEncoded(), "AES");
   }

   /**
    * Helper to convert hex strings to bytes.
    *
    * May be used to read bytes from constants.
    */
   private static byte[] hexStringToByteArray(String s) {
	  if (s == null) {
		 throw new IllegalArgumentException("Provided `null` string.");
	  }
	  int len = s.length();
	  if (len % 2 != 0) {
		 throw new IllegalArgumentException("Invalid length: " + len);
	  }
	  byte[] data = new byte[len / 2];
	  for (int i = 0; i < len - 1; i += 2) {
		 byte b = (byte) toHexDigit(s, i);
		 b <<= 4;
		 b |= toHexDigit(s, i + 1);
		 data[i / 2] = b;
	  }
	  return data;
   }

   private static int toHexDigit(String s, int pos) {
	  int d = Character.digit(s.charAt(pos), 16);
	  if (d < 0) {
		 throw new IllegalArgumentException("Cannot parse hex digit: " + s + " at " + pos);
	  }
	  return d;
   }

   public static byte[] getPBKDF2_SALT() { return PBKDF2_SALT; }

   public static void setPBKDF2_SALT(byte[] pBKDF2_SALT) { PBKDF2_SALT = pBKDF2_SALT; }
}