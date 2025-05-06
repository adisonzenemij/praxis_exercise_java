
package code.cypher.symtrcl;

import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Scanner;

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

        // 1. Solicitar texto a codificar
        System.out.print("Ingrese el texto a codificar: ");
        String data = scanner.nextLine();
        
        // 2. Aplicar hash
        System.out.print("¿Desea aplicar un hash antes de cifrar? (NINGUNO/MD2/MD5/SHA-1/SHA-224/SHA-256/SHA-384/SHA-512/SHA-512/224/SHA-512/256): ");
        String hashOption = scanner.nextLine().trim().toUpperCase();

        switch (hashOption) {
            case "MD2", "MD5", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512", "SHA-512/224", "SHA-512/256" -> data = hashText(data, hashOption);
            case "NINGUNO" -> {
            }
            default -> {
                System.out.println("Opción no válida. Use una de las siguientes: NINGUNO, MD2, MD5, SHA-1, SHA-224, SHA-256, SHA-384, SHA-512, SHA-512/224, SHA-512/256.");
                return;
            }
        }

        // 2. Solicitar formato de salida
        System.out.print("Seleccione el formato de salida (HEX/Base64): ");
        String outputFormat = scanner.nextLine().trim().toUpperCase();

        // Validar formato de salida
        if (!outputFormat.equals("HEX") && !outputFormat.equals("BASE64")) {
            System.out.println("Formato no válido. Use HEX o Base64.");
            return;
        }

        // 3. Solicitar tamaño de la clave
        System.out.print("Ingrese el tamanio de la clave (128, 192 o 256): ");
        int keySize = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        if (keySize != 128 && keySize != 192 && keySize != 256) {
            System.out.println("Tamaño de clave no válido. Use 128, 192 o 256.");
            return;
        }

        // Generar clave
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keySize);
        SecretKey secretKey = keyGen.generateKey();

        // Cifrado
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());

        String encryptedText;
        if (outputFormat.equals("HEX")) {
            encryptedText = bytesToHex(encrypted);
        } else {
            encryptedText = Base64.getEncoder().encodeToString(encrypted);
        }

        System.out.println("Texto cifrado (" + outputFormat + "): " + encryptedText);

        // Descifrado (solo para mostrar que funciona)
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decrypted;
        if (outputFormat.equals("HEX")) {
            decrypted = cipher.doFinal(hexToBytes(encryptedText));
        } else {
            decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        }
        //String decryptedText = new String(decrypted);
        //System.out.println("Texto descifrado: " + decryptedText);

        String decryptedText = new String(decryptedBytes);
        System.out.println("Texto descifrado (hash aplicado): " + decryptedText);
    }

    // Función para aplicar hash
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
