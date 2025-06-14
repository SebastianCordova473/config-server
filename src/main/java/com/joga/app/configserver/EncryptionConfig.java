package com.joga.app.configserver;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.joga.app.configserver.Contants.*;

@Configuration
public class EncryptionConfig {

    @Bean(JASYPT_STRING_ENCRYPTOR)
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword(System.getenv(JASYPT_ENCRYPTOR_PASSWORD));
        config.setAlgorithm(PBE_WITH_HMACSHA_512_AND_AES_256);
        config.setKeyObtentionIterations(KEY_OBTENTION_ITERATIONS);
        config.setPoolSize(1);
        config.setProviderName(SUN_JCE);
        config.setSaltGeneratorClassName(ORG_JASYPT_SALT_RANDOM_SALT_GENERATOR);
        config.setIvGeneratorClassName(ORG_JASYPT_IV_RANDOM_IV_GENERATOR);
        config.setStringOutputType(BASE_64);
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
            return CIPHER + delegate.encrypt(message);
        }

        @Override
        public String decrypt(String encryptedMessage) {
            if (encryptedMessage.startsWith(CIPHER1)) {
                return delegate.decrypt(encryptedMessage.substring(9));
            } else if (encryptedMessage.startsWith(CIPHER)) {
                return delegate.decrypt(encryptedMessage.substring(8));
            }
            return delegate.decrypt(encryptedMessage);
        }
    }
}