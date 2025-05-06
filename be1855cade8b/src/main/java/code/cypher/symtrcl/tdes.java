
package code.cypher.symtrcl;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class tdes {
    public static void test() throws Exception {
        String data = "ABC123";

        // Generar la clave 3DES
        KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
        keyGen.init(168);
        SecretKey secretKey = keyGen.generateKey();

        // Cifrar
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encrypted);
        System.out.println("Encrypted 3DES: " + encryptedText);

        // Desencriptar
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        String decryptedText = new String(decrypted);
        System.out.println("Decrypted 3DES: " + decryptedText);
    }
}
