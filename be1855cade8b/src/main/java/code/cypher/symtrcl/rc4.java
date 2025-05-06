
package code.cypher.symtrcl;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class rc4 {
    public static void test() throws Exception {
        String data = "ABC123";
        
        // Clave para RC4
        String key = "1234567890ABCDEF"; // Debe ser una clave de 16 bytes para RC4 (128 bits)

        // Cifrado RC4
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "RC4");
        Cipher cipher = Cipher.getInstance("RC4");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encryptedData);
        System.out.println("Encrypted RC4: " + encryptedText);

        // Desencriptado RC4
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        String decryptedText = new String(decryptedData);
        System.out.println("Decrypted RC4: " + decryptedText);
    }
}
