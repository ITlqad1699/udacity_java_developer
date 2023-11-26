package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * The type Encryption service.
 */
@Service
public class EncryptionService {
    private final Logger logger = LoggerFactory.getLogger(EncryptionService.class);
    
    /**
     * Encrypt value string.
     *
     * @param data the data
     * @param key  the key
     * @return the string
     */
    public String encryptValue(String data, String key) {
        byte[] encryptedValue = null;
        
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedValue = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                 | IllegalBlockSizeException | BadPaddingException e) {
            logger.error(e.getMessage());
        }
        
        return Base64.getEncoder().encodeToString(encryptedValue);
    }
    
    /**
     * Decrypt value string.
     *
     * @param data the data
     * @param key  the key
     * @return the string
     */
    public String decryptValue(String data, String key) {
        byte[] decryptedValue = null;
        
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedValue = cipher.doFinal(Base64.getDecoder().decode(data));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                 | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            logger.error(e.getMessage());
        }
        
        return new String(decryptedValue);
    }
    
    /**
     * Generate key string.
     *
     * @return the string
     */
    public String generateKey() {
        try {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            return Base64.getEncoder().encodeToString(key);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }
}