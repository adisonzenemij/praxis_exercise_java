
package code.codification;

public class binary {
    // Convertir de Bytes a Binario
    public static String bytesToBinary(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        return sb.toString();
    }

    // Convertir de Binario a Bytes
    public static byte[] binaryToBytes(String binary) {
        int len = binary.length() / 8;
        byte[] data = new byte[len];
        for (int i = 0; i < len; i++) {
            String byteString = binary.substring(i * 8, (i + 1) * 8);
            data[i] = (byte) Integer.parseInt(byteString, 2);
        }
        return data;
    }
}
