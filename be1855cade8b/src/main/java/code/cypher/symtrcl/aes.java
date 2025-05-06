
package code.cypher.symtrcl;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class aes {
    public static void test() throws Exception {
        /*String data = "ABC123";
        
        // Generar la clave AES
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        // (16 bytes = 128) | (24 bytes = 192) | (32 bytes = 256)
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();

        // Cifrar
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encrypted);
        System.out.println("Encrypted AES: " + encryptedText);

        // Desencriptar
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        String decryptedText = new String(decrypted);
        System.out.println("Decrypted AES: " + decryptedText);*/
        
        Scanner scanner = new Scanner(System.in);

        // 1. Ingreso de texto
        System.out.print("Ingrese el texto a codificar: ");
        String data = scanner.nextLine();

        // 3. Formato de salida
        System.out.print("Seleccione el formato de salida (HEX/Base64): ");
        String outputFormat = scanner.nextLine().trim().toUpperCase();

        if (!outputFormat.equals("HEX") && !outputFormat.equals("BASE64")) {
            System.out.println("Formato no válido. Use HEX o Base64.");
            return;
        }

        // 4. Tamaño de clave
        System.out.print("Ingrese el tamaño de la clave (128, 192 o 256): ");
        int keySize = scanner.nextInt();
        scanner.nextLine();

        if (keySize != 128 && keySize != 192 && keySize != 256) {
            System.out.println("Tamaño de clave no válido. Use 128, 192 o 256.");
            return;
        }

        // Generar clave AES
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keySize);
        SecretKey secretKey = keyGen.generateKey();

        // Cifrar
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());

        String encryptedText = outputFormat.equals("HEX") ?
                bytesToHex(encrypted) :
                Base64.getEncoder().encodeToString(encrypted);

        System.out.println("Texto cifrado (" + outputFormat + "): " + encryptedText);

        // Descifrar (para validación)
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = outputFormat.equals("HEX") ?
                cipher.doFinal(hexToBytes(encryptedText)) :
                cipher.doFinal(Base64.getDecoder().decode(encryptedText));

        String decryptedText = new String(decryptedBytes);
        System.out.println("Texto descifrado (hash aplicado): " + decryptedText);
    }

    // Funcion para aplicar hash
    public static String hashText(String input, String algorithm) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] hashBytes = digest.digest(input.getBytes());
        return bytesToHex(hashBytes);
    }
    
    // Convertir bytes a HEX
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    // Convertir HEX a bytes
    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }
}
