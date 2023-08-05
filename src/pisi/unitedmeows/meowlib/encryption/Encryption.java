package pisi.unitedmeows.meowlib.encryption;

import java.text.ParseException;

/**
 * @author exceptions are made by Thomas Richner
 */
import pisi.unitedmeows.meowlib.encryption.exceptions.BadMacTagException;
import pisi.unitedmeows.meowlib.encryption.exceptions.BadVersionException;
import pisi.unitedmeows.meowlib.encryption.exceptions.CryptoException;

public interface Encryption {
   String encrypt(char[] secret, String plaintext) throws CryptoException;

   String decrypt(char[] secret, String ciphertext)
		 throws CryptoException,BadVersionException,BadMacTagException,ParseException;
}
