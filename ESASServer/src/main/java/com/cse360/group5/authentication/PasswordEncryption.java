package com.cse360.group5.authentication;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordEncryption {

    final static String algorithm = "SHA1";

    /**
     * Encrypts passwords using the SHA1 hash algorithm.
     *
     * @param password
     * @return
     */
    public static String encrypt(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] bytes = messageDigest.digest(password.getBytes());
            String encryptedPassword = Base64.encodeBase64String(bytes);
            return encryptedPassword;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
