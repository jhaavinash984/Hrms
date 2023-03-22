package com.ssa.hrms.security.util;

import com.ssa.hrms.security.exception.EncryptionException;

public interface Encryptor {
	

	public String encrypt(String plainText) throws EncryptionException;

	public String decrypt(String cipherText) throws EncryptionException;

}
