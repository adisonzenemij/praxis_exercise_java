
package code.codification;

public class octal {
    // Convertir de Bytes a Octal
    public static String bytesToOctal(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%03o", b & 0xFF));
        return sb.toString();
    }

    // Convertir de Octal a Bytes
    public static byte[] octalToBytes(String octal) {
        int len = octal.length() / 3;
        byte[] data = new byte[len];
        for (int i = 0; i < len; i++) {
            String byteString = octal.substring(i * 3, (i + 1) * 3);
            data[i] = (byte) Integer.parseInt(byteString, 8);
        }
        return data;
    }
}
