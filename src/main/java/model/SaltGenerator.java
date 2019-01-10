package model;

import java.security.SecureRandom;
import java.util.Base64;

public class SaltGenerator {
    
    public static String generate() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String result = Base64.getEncoder().encodeToString(salt);
        return result.substring(0, 20);
    }
}
