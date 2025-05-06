
package code.codification;

import java.io.ByteArrayOutputStream;

public class utf8 {
    // Convertir de Bytes a UTF-8 escapado
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

    // Convertir de UTF-8 escapado a Bytes
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
