package com.ssa.hrms.common;

import java.io.IOException;
import java.util.Properties;


public final class Constants {
	
	public final static String PROPSFILE = "application.properties";

	private static Properties props = new Properties();

	static {
		try {
			props.load(Constants.class.getClassLoader().getResourceAsStream(PROPSFILE));
		} catch (IOException e) {
			System.exit(1); // TODO: load default properties
		}
	}
	
	public static final int RESULT_SUCCESS_CODE = 200;
	public static final int RESULT_FAILED_CODE = 500;
	public static final int RESULT_UNAUTHORIZED_CODE = 401;
	public static final int RESULT_FORBIDDEN_CODE = 403;
	public static final int RESULT_BADREQUEST_CODE = 400;
	public static final int RESULT_NOTFOUND_CODE = 404;
	
	public static final String ENCRYPTOR_RANDOM_ALGORITHM = props.getProperty("hrms.encryptor.random.algorithm");
	public static final String ENCRYPTOR_RANDOM_PROVIDER = props.getProperty("hrms.encryptor.random.provider");
	public static final Integer ENCRYPTOR_ENCODER_STRENGTH = getIntegerProperty("hrms.encryptor.encoder.strength",10);
	public static final String ENCRYPTION_ALGORITHM = props.getProperty("hrms.encryptor.encryption.algorithm");
	public static final int ENC_KEY_SIZE = getIntegerProperty("hrms.encryptor.key.length", 128);
	public static final int TAG_BIT_LENGTH = getIntegerProperty("hrms.encryptor.tag.length", 128);
	public static final String ENC_ALGO_TRANSFORMATION_STRING = props
			.getProperty("hrms.encryptor.cipher.transformation");
	public static final String FIXED_IV = props.getProperty("hrms.encryptor.fixedIV");
	public static final String FIXED_TAG = props.getProperty("hrms.encryptor.fixedTag");
	public static final String SECRET_KEY = props.getProperty("hrms.encryptor.secretKey");
	
	private Constants() {
	}

	private static int getIntegerProperty(String propName, int defaultVal) {
		String prop = props.getProperty(propName);
		if (prop == null || prop.isEmpty()) {
			return defaultVal;
		} else
			return Integer.parseInt(prop);
	}

}
