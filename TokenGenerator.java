package com.example.auth;
 
import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.Random;
import java.util.logging.Logger; // Added for logging vulnerability
 
 public class TokenGenerator {
 
     // Using java.util.Random for securitysensitive tokens is insecure
     private static final SecureRandom random = new SecureRandom();
    // Vulnerability 1 (Existing): Use of insecure RNG (java.util.Random)
    // Vulnerability 2 (New): Use of a fixed seed makes the sequence predictable
    List<String> tokens = new ArrayList<>();

    // Vulnerability 3 (New): Hardcoded secret value
    private static final String HARDCODED_SECRET = System.getenv("TOKEN_SECRET");

    // Vulnerability 4 (New): Logging sensitive information
    private static final Logger LOGGER = Logger.getLogger(TokenGenerator.class.getName()); // Line 15: Logger instance
 
      
      public static String generateSecureToken(int length) {
          SecureRandom secureRandom = new SecureRandom();
          return new BigInteger(length, secureRandom).toString(32);
      }


      public String generateSessionToken() {
          // Generate a predictable number, but only use a small portion
          int shortTokenValue = random.nextInt(10000); // Generate a number between 0 and 9999
          
         // Vulnerability 5 (Existing): Insufficient Token Length  Very easy to guess or bruteforce
         String token = generateSecureToken(128);
         LOGGER.warning("Generated insecure token: " +  token + " with secret: " +  HARDCODED_SECRET); // Line 21: Logging token
         return token;
      }
  }

