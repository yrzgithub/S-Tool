import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class UserAuthentication {

    // Sample method to demonstrate password hashing vulnerability
    public String hashPassword(String password) {
        // Vulnerable line using MD5 for hashing
        // Vulnerability: Weak Password Hashing Algorithm
        String passwordHash = md5Hash(password);
        return passwordHash;
    }
    
    // Vulnerable method using MD5
    private String md5Hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  // Line 45
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Recommendation: Use bcrypt for password hashing instead of MD5
    // Sample code to demonstrate using bcrypt
    public String secureHashPassword(String password) {
        return org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
    }

    public static void main(String[] args) {
        UserAuthentication auth = new UserAuthentication();
        String password = "securePassword123";
        
        // VULNERABLE - MD5 hashing demonstration
        String weakHash = auth.hashPassword(password);
        System.out.println("Weak MD5 hashed password: " + weakHash);
        
        // FIXED - bcrypt hashing demonstration
        String secureHash = auth.secureHashPassword(password);
        System.out.println("Secure bcrypt hashed password: " + secureHash);
    }
}