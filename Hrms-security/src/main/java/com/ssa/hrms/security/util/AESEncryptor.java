package com.ssa.hrms.security.util;


import java.security.InvalidAlgorithmParameterException ;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException ;
import java.security.SecureRandom ;
import java.util.Base64 ;

import javax.crypto.BadPaddingException ;
import javax.crypto.Cipher ;
import javax.crypto.IllegalBlockSizeException ;
import javax.crypto.NoSuchPaddingException ;
import javax.crypto.spec.GCMParameterSpec ;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ssa.hrms.security.codec.Hex;
import com.ssa.hrms.security.exception.EncryptionException;

import static com.ssa.hrms.common.Constants.ENCRYPTION_ALGORITHM;
import static com.ssa.hrms.common.Constants.ENC_ALGO_TRANSFORMATION_STRING;
import static com.ssa.hrms.common.Constants.FIXED_IV;
import static com.ssa.hrms.common.Constants.FIXED_TAG;
import static com.ssa.hrms.common.Constants.SECRET_KEY;
import static com.ssa.hrms.common.Constants.TAG_BIT_LENGTH;

/**
        This class shows how to securely perform AES encryption in GCM mode, with 128 bits key size.
*/
@Service
public class AESEncryptor implements Encryptor{

		private static final Logger Log = LoggerFactory.getLogger(AESEncryptor.class);
	
        private static final SecretKeySpec secretKey;

        private static final byte[] aadData = FIXED_TAG.getBytes();
        private static final GCMParameterSpec gcmParamSpec = new GCMParameterSpec(TAG_BIT_LENGTH , Hex.decode(FIXED_IV));
        
		static {
			secretKey = new SecretKeySpec(Hex.decode(SECRET_KEY), ENCRYPTION_ALGORITHM);
		}


        private byte[] aesEncrypt(String message) throws EncryptionException {
			try {
				Cipher c = Cipher.getInstance(ENC_ALGO_TRANSFORMATION_STRING); // Transformation specifies algortihm, mode of operation and padding
	
				c.init(Cipher.ENCRYPT_MODE, secretKey, gcmParamSpec, new SecureRandom());
	
				c.updateAAD(aadData); // add AAD tag data before encrypting
	
				return c.doFinal(message.getBytes());
	
			} catch (NoSuchAlgorithmException noSuchAlgoExc) {
					throw new EncryptionException("Exception while encrypting. Algorithm being requested is not available in this environment "	, noSuchAlgoExc);
				} catch (NoSuchPaddingException noSuchPaddingExc) {
					throw new EncryptionException("Exception while encrypting. Padding Scheme being requested is not available this environment ", noSuchPaddingExc);
				} catch (InvalidKeyException invalidKeyExc) {
					throw new EncryptionException("Exception while encrypting. Key being used is not valid. It could be due to invalid encoding, wrong length or uninitialized ", invalidKeyExc);
				} catch (InvalidAlgorithmParameterException invalidAlgoParamExc) {
					throw new EncryptionException("Exception while encrypting. Algorithm parameters being specified are not valid "	, invalidAlgoParamExc);
				} catch (IllegalArgumentException illegalArgumentExc) {
					throw new EncryptionException("Exception thrown while encrypting. Byte array might be null " , illegalArgumentExc);
				} catch (IllegalStateException illegalStateExc) {
					throw new EncryptionException("Exception thrown while encrypting. CIpher is in an illegal state " , illegalStateExc);
				} catch (UnsupportedOperationException unsupportedExc) {
					throw new EncryptionException("Exception thrown while encrypting. Provider might not be supporting this method ", unsupportedExc);
				} catch (IllegalBlockSizeException illegalBlockSizeExc) {
					throw new EncryptionException("Exception while encrypting, due to block size " , illegalBlockSizeExc);
				} catch (BadPaddingException badPaddingExc) {
					throw new EncryptionException("Exception while encrypting, due to padding scheme " , badPaddingExc);
				}

        }


        private  byte[] aesDecrypt(byte[] encryptedMessage) throws EncryptionException {
			try {
				Cipher c = Cipher.getInstance(ENC_ALGO_TRANSFORMATION_STRING);
	
				c.init(Cipher.DECRYPT_MODE, secretKey, gcmParamSpec, new SecureRandom());
	
				c.updateAAD(aadData);
	
				return c.doFinal(encryptedMessage);
	
			} catch (NoSuchAlgorithmException noSuchAlgoExc) {
				throw new EncryptionException("Exception while decrypting. Algorithm being requested is not available in environment ",	noSuchAlgoExc);
			} catch (NoSuchPaddingException noSuchAlgoExc) {
				throw new EncryptionException("Exception while decrypting. Padding scheme being requested is not available in environment ",noSuchAlgoExc);
			} catch (InvalidKeyException invalidKeyExc) {
				throw new EncryptionException("Exception while encrypting. Key being used is not valid. It could be due to invalid encoding, wrong length or uninitialized ",invalidKeyExc);
			} catch (InvalidAlgorithmParameterException invalidParamSpecExc) {
				throw new EncryptionException("Exception while encrypting. Algorithm Param being used is not valid. ",	invalidParamSpecExc);
			} catch (IllegalArgumentException illegalArgumentExc) {
				throw new EncryptionException("Exception thrown while encrypting. Byte array might be null ",	illegalArgumentExc);
			} catch (IllegalStateException illegalStateExc) {
				throw new EncryptionException("Exception thrown while encrypting. CIpher is in an illegal state ",	illegalStateExc);
			} catch (IllegalBlockSizeException illegalBlockSizeExc) {
				throw new EncryptionException("Exception while decryption, due to block size ", illegalBlockSizeExc);
			} catch (BadPaddingException badPaddingExc) {
				throw new EncryptionException("Exception while decryption, due to padding scheme ", badPaddingExc);
			}

        }


		@Override
		public String encrypt(String plainText) throws EncryptionException {
			try {
				return Base64.getEncoder().encodeToString(aesEncrypt(plainText));
			} catch (EncryptionException e) {
				Log.error(e.getMessage());
				throw e;
			}
	
		}
	
		@Override
		public String decrypt(String encryptedText) throws EncryptionException {
			try {
				byte[] decryptedText = aesDecrypt(Base64.getDecoder().decode(encryptedText.getBytes()));
				return new String(decryptedText);
			} catch (EncryptionException e) {
				Log.error(e.getMessage());
				throw e;
			}
		}
		
		
}
