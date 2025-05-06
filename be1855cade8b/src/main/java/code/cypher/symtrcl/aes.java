
package code.cypher.symtrcl;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class aes {
    public static void test() throws Exception {
        Scanner scanner = new Scanner(System.in);

        // 1. Ingreso de texto
        System.out.print("Ingrese el texto a codificar: ");
        String data = scanner.nextLine();

        // 2. Aplicar hash solo para verificación (no cifrar el hash)
        System.out.print("¿Desea aplicar un hash para verificación? (NINGUNO/MD2/MD5/SHA-1/SHA-224/SHA-256/SHA-384/SHA-512/SHA-512/224/SHA-512/256): ");
        String hashOption = scanner.nextLine().trim().toUpperCase();

        String hashValue = null;

        switch (hashOption) {
            case "MD2":
            case "MD5":
            case "SHA-1":
            case "SHA-224":
            case "SHA-256":
            case "SHA-384":
            case "SHA-512":
            case "SHA-512/224":
            case "SHA-512/256":
                hashValue = hashText(data, hashOption);
                System.out.println("Hash del texto original: " + hashValue);
                break;
            case "NINGUNO":
                break;
            default:
                System.out.println("Opción no válida. Use una opción válida de hash.");
                return;
        }

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

        // Cifrar el texto original
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());

        String encryptedText = outputFormat.equals("HEX") ?
                bytesToHex(encrypted) :
                Base64.getEncoder().encodeToString(encrypted);

        System.out.println("Texto cifrado (" + outputFormat + "): " + encryptedText);

        // Descifrar
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = outputFormat.equals("HEX") ?
                cipher.doFinal(hexToBytes(encryptedText)) :
                cipher.doFinal(Base64.getDecoder().decode(encryptedText));

        String decryptedText = new String(decryptedBytes);
        System.out.println("Texto descifrado: " + decryptedText);

        // Validar hash si se generó
        if (hashValue != null) {
            String recalculatedHash = hashText(decryptedText, hashOption);
            boolean isValid = recalculatedHash.equals(hashValue);
            System.out.println("¿Hash válido tras descifrado? " + isValid);
        }
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
