
package code.cypher.symtrcl;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class twofish {
    public static void test() throws Exception {
        String data = "ABC123";

        // Generar la clave Twofish
        KeyGenerator keyGen = KeyGenerator.getInstance("Twofish");
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();

        // Cifrar
        Cipher cipher = Cipher.getInstance("Twofish");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encrypted);
        System.out.println("Encrypted Twofish: " + encryptedText);

        // Desencriptar
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        String decryptedText = new String(decrypted);
        System.out.println("Decrypted Twofish: " + decryptedText);

    }
}
