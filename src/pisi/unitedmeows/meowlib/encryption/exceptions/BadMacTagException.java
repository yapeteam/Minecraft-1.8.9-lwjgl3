package pisi.unitedmeows.meowlib.encryption.exceptions;

public class BadMacTagException extends Exception {
	public BadMacTagException() {
	}

	public BadMacTagException(String message) {
		super(message);
	}

	public BadMacTagException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadMacTagException(Throwable cause) {
		super(cause);
	}

	public BadMacTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}