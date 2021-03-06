import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


class DES {
    private static Cipher encipher;
    private static Cipher decipher;
    private static SecretKey key;
    public static void main(String[] args) {

        try {
            // generate Secret key using DES algorithm
            key = KeyGenerator.getInstance("DES").generateKey();
            encipher = Cipher.getInstance("DES");
            decipher = Cipher.getInstance("DES");

            encipher.init(Cipher.ENCRYPT_MODE, key);
            decipher.init(Cipher.DECRYPT_MODE, key);

            String plaintext = "I Giorno Giovana have a dream.";

            long startTime, endTime;
            String encrypted, decrypted;

            startTime = System.nanoTime();
            encrypted = encrypt(plaintext);
            endTime = System.nanoTime();

            long encryptDuration = (endTime - startTime);

            startTime = System.nanoTime();
            decrypted = decrypt(encrypted);
            endTime = System.nanoTime();

            long decryptDuration = (endTime - startTime);

            System.out.println("Encrypted: " + encrypted);
            System.out.println("Decrypted: " + decrypted);

            System.out.println("encryption duration: " + encryptDuration + " ns");
            System.out.println("Decryption duration: " + decryptDuration + " ns");

        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such Algorithm: " + e.getMessage());
        } catch (NoSuchPaddingException e) {
            System.out.println("No such Padding " + e.getMessage());
        } catch (InvalidKeyException e) {
            System.out.println("Invalid Key: " + e.getMessage());
        }
    }

    public static String encrypt(String str) {
        try {
            // encode the string into a sequence of bytes using the named charset
            // storing the result into a new byte array.
            byte[] utf8 = str.getBytes(StandardCharsets.UTF_8);
            byte[] enc = encipher.doFinal(utf8);
            // encode to base64
            return Base64.getEncoder().encodeToString(enc);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String str) {
        try {
            // decode with base64 to get bytes
            byte[] dec = Base64.getDecoder().decode(str.getBytes());
            byte[] utf8 = decipher.doFinal(dec);
            // create new string based on the specified charset
            return new String(utf8, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}