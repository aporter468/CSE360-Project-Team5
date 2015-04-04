package com.cse360.group5.authentication;


import org.mindrot.jbcrypt.BCrypt;

public final class PasswordEncryption {

    /**
     * Encrypts passwords using the bcrypt hash algorithm.
     *
     * @param password
     * @return
     */
    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Checks that the unencrypted password hashes to the hashed password.
     *
     * @param password
     * @param hashedPassword
     * @return
     */
    public static boolean check(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
