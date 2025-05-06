
package code.cypher.symtrcl;

import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class des {
    public static void test() throws Exception {
        /*String data = "ABC123";
        
        // Generar la clave DES
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56);
        SecretKey secretKey = keyGen.generateKey();

        // Cifrar
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encrypted);
        System.out.println("Encrypted DES: " + encryptedText);

        // Desencriptar
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        String decryptedText = new String(decrypted);
        System.out.println("Decrypted DES: " + decryptedText);*/
        
        Scanner scanner = new Scanner(System.in);

        // 1. Ingreso de texto
        System.out.print("Ingrese el texto a codificar: ");
        String data = scanner.nextLine();

        // 2. Formato de salida
        System.out.print("Seleccione el formato de salida (HEX, BASE64, BINARIO, OCTAL, UTF8): ");
        String outputFormat = scanner.nextLine().trim().toUpperCase();

        // Validación
        if (!outputFormat.matches("HEX|BASE64|BINARIO|OCTAL|UTF8")) {
            System.out.println("Formato no válido.");
            return;
        }

        // 3. Tamaño de clave
        System.out.print("Ingrese el tamaño de la clave (56, 192 o 256): ");
        int keySize = scanner.nextInt();
        scanner.nextLine();

        if (keySize != 56 && keySize != 192 && keySize != 256) {
            System.out.println("Tamaño de clave no válido. Use 56, 192 o 256.");
            return;
        }

        // Generar clave DES
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(keySize);
        SecretKey secretKey = keyGen.generateKey();

        // Cifrar
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());

        String encryptedText;

        switch (outputFormat) {
            case "HEX":
                encryptedText = code.codification.hex.bytesToHex(encrypted);
                break;
            case "BASE64":
                encryptedText = code.codification.base64.bytesToBase64(encrypted);
                break;
            case "BINARIO":
                encryptedText = code.codification.binary.bytesToBinary(encrypted);
                break;
            case "OCTAL":
                encryptedText = code.codification.octal.bytesToOctal(encrypted);
                break;
            case "UTF8":
                encryptedText = code.codification.utf8.escapeUTF8(encrypted);
                break;
            default:
                encryptedText = "";
        }

        System.out.println("Texto cifrado (" + outputFormat + "): " + encryptedText);

        // Descifrado (solo para formatos binarios posibles de decodificar)
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes;

        switch (outputFormat) {
            case "HEX":
                decryptedBytes = cipher.doFinal(code.codification.hex.hexToBytes(encryptedText));
                break;
            case "BASE64":
                decryptedBytes = cipher.doFinal(code.codification.base64.base64ToBytes(encryptedText));
                break;
            case "BINARIO":
                decryptedBytes = cipher.doFinal(code.codification.binary.binaryToBytes(encryptedText));
                break;
            case "OCTAL":
                decryptedBytes = cipher.doFinal(code.codification.octal.octalToBytes(encryptedText));
                break;
            case "UTF8":
                decryptedBytes = cipher.doFinal(code.codification.utf8.unescapeUTF8(encryptedText));
                break;
            default:
                System.out.println("Descifrado no soportado para este formato.");
                return;
        }

        String decryptedText = new String(decryptedBytes);
        System.out.println("Texto descifrado: " + decryptedText);

    }
}
