package com.ssa.hrms.security.exception;

/**
 * An EncryptionException should be thrown for any problems related to
 * encryption, hashing, or digital signatures.
 */
public class EncryptionException extends RuntimeException {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	protected EncryptionException(String message) {
		super(message);
	}

	public EncryptionException(String message, Throwable cause) {
		super(message, cause);
	}

	public EncryptionException(Throwable cause) {
		super(cause);
	}
}
