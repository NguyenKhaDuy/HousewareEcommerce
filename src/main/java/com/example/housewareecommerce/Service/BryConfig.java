package com.example.housewareecommerce.Service;

import de.svws_nrw.ext.jbcrypt.BCrypt;

public class BryConfig {
    private static final int COST = 12;

    private BryConfig() {}

    public static String hash(String rawPassword) {
        String salt = BCrypt.gensalt(COST);
        return BCrypt.hashpw(rawPassword, salt);
    }

    public static boolean matches(String rawPassword, String hashedPassword) {
        if (hashedPassword == null || hashedPassword.isEmpty()) {
            return false;
        }
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
