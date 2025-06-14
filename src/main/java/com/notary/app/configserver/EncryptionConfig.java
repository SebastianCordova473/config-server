package com.notary.app.configserver;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionConfig {

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword(System.getenv("JASYPT_ENCRYPTOR_PASSWORD"));
        config.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        config.setKeyObtentionIterations(1000);
        config.setPoolSize(1);
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        return new CustomPrefixEncryptor(encryptor);
    }

    static class CustomPrefixEncryptor implements StringEncryptor {
        private final StringEncryptor delegate;

        public CustomPrefixEncryptor(StringEncryptor delegate) {
            this.delegate = delegate;
        }

        @Override
        public String encrypt(String message) {
            return "{cipher}" + delegate.encrypt(message);
        }

        @Override
        public String decrypt(String encryptedMessage) {
            if (encryptedMessage.startsWith("{cipher:}")) {
                return delegate.decrypt(encryptedMessage.substring(9));
            } else if (encryptedMessage.startsWith("{cipher}")) {
                return delegate.decrypt(encryptedMessage.substring(8));
            }
            return delegate.decrypt(encryptedMessage);
        }
    }
}