package com.joga.app.configserver;

import org.jasypt.encryption.StringEncryptor;

public class Contants {

    public static final String JASYPT_ENCRYPTOR_PASSWORD = "JASYPT_ENCRYPTOR_PASSWORD";
    public static final String PBE_WITH_HMACSHA_512_AND_AES_256 = "PBEWithHMACSHA512AndAES_256";
    public static final int KEY_OBTENTION_ITERATIONS = 1000;
    public static final String SUN_JCE = "SunJCE";
    public static final String ORG_JASYPT_SALT_RANDOM_SALT_GENERATOR = "org.jasypt.salt.RandomSaltGenerator";
    public static final String ORG_JASYPT_IV_RANDOM_IV_GENERATOR = "org.jasypt.iv.RandomIvGenerator";
    public static final String BASE_64 = "base64";
    public static final String JASYPT_STRING_ENCRYPTOR = "jasyptStringEncryptor";
    public static final String CIPHER = "{cipher}";
    public static final String CIPHER1 = "{cipher:}";
}
