
package code.cypher.symtrcl;

import java.io.ByteArrayOutputStream;
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

        // 2. Formato de salida
        System.out.print("Seleccione el formato de salida (HEX, BASE64, BINARIO, OCTAL, UTF8): ");
        String outputFormat = scanner.nextLine().trim().toUpperCase();

        // Validación
        if (!outputFormat.matches("HEX|BASE64|BINARIO|OCTAL|UTF8")) {
            System.out.println("Formato no válido.");
            return;
        }

        // 3. Tamaño de clave
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

        String encryptedText;

        switch (outputFormat) {
            case "HEX":
                encryptedText = bytesToHex(encrypted);
                break;
            case "BASE64":
                encryptedText = Base64.getEncoder().encodeToString(encrypted);
                break;
            case "BINARIO":
                encryptedText = bytesToBinary(encrypted);
                break;
            case "OCTAL":
                encryptedText = bytesToOctal(encrypted);
                break;
            case "UTF8":
                encryptedText = escapeUTF8(encrypted);
                break;
            default:
                encryptedText = "";
        }

        System.out.println("Texto cifrado (" + outputFormat + "): " + encryptedText);

        // Descifrar (para validación)
        /*cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = outputFormat.equals("HEX") ?
                cipher.doFinal(hexToBytes(encryptedText)) :
                cipher.doFinal(Base64.getDecoder().decode(encryptedText));

        String decryptedText = new String(decryptedBytes);
        System.out.println("Texto descifrado (hash aplicado): " + decryptedText);*/

        // Descifrado (solo para formatos binarios posibles de decodificar)
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes;

        switch (outputFormat) {
            case "HEX":
                decryptedBytes = cipher.doFinal(hexToBytes(encryptedText));
                break;
            case "BASE64":
                decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
                break;
            case "BINARIO":
                decryptedBytes = cipher.doFinal(binaryToBytes(encryptedText));
                break;
            case "OCTAL":
                decryptedBytes = cipher.doFinal(octalToBytes(encryptedText));
                break;
            case "UTF8":
                decryptedBytes = cipher.doFinal(unescapeUTF8(encryptedText));
                break;
            default:
                System.out.println("Descifrado no soportado para este formato.");
                return;
        }

        String decryptedText = new String(decryptedBytes);
        System.out.println("Texto descifrado: " + decryptedText);

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

    // BINARIO
    public static String bytesToBinary(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        return sb.toString();
    }

    // OCTAL
    public static String bytesToOctal(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%03o", b & 0xFF));
        return sb.toString();
    }

    // UTF-8 escapado
    public static String escapeUTF8(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            char c = (char) (b & 0xFF);
            if (Character.isISOControl(c) || c > 127) {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    // De BINARIO a bytes
    public static byte[] binaryToBytes(String binary) {
        int len = binary.length() / 8;
        byte[] data = new byte[len];
        for (int i = 0; i < len; i++) {
            String byteString = binary.substring(i * 8, (i + 1) * 8);
            data[i] = (byte) Integer.parseInt(byteString, 2);
        }
        return data;
    }

    // De OCTAL a bytes
    public static byte[] octalToBytes(String octal) {
        int len = octal.length() / 3;
        byte[] data = new byte[len];
        for (int i = 0; i < len; i++) {
            String byteString = octal.substring(i * 3, (i + 1) * 3);
            data[i] = (byte) Integer.parseInt(byteString, 8);
        }
        return data;
    }

    // De UTF-8 escapado a bytes
    public static byte[] unescapeUTF8(String escaped) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < escaped.length(); ) {
            if (escaped.charAt(i) == '\\' && i + 5 < escaped.length() && escaped.charAt(i + 1) == 'u') {
                String hex = escaped.substring(i + 2, i + 6);
                baos.write((char) Integer.parseInt(hex, 16));
                i += 6;
            } else {
                baos.write((byte) escaped.charAt(i));
                i++;
            }
        }
        return baos.toByteArray();
    }

}
