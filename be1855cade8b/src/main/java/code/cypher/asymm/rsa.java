
package code.cypher.asymm;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.Signature;
import java.security.PrivateKey;
import java.util.Base64;

public class rsa {
    public static void test() throws Exception {
        String data = "ABC123";

        // Longitud deseada de la clave
        int keySize = 2048;
        
        // Generar par de claves RSA
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(keySize);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        
        // Mostrar información sobre la clave
        System.out.println("Key Size (bits): " + keySize);
        System.out.println("Public Key Size (bytes): " + publicKey.getEncoded().length);
        System.out.println("Private Key Size (bytes): " + privateKey.getEncoded().length);

        // Mostrar clave pública en formato PEM
        System.out.println("\nClave publica en formato PEM:");
        System.out.println(convertToPEMPublicKey(publicKey));

        // Mostrar clave privada en formato PEM (PKCS#8)
        System.out.println("\nClave privada en formato PEM (PKCS#8):");
        System.out.println(convertToPEMPrivateKey(privateKey));
        
        // Mostrar la clave pública
        //String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        //System.out.println("Public Key (Base64): " + publicKeyBase64);

        // Cifrar
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encrypted);
        System.out.println("Encrypted RSA: " + encryptedText);

        // Desencriptar
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        String decryptedText = new String(decrypted);
        System.out.println("Decrypted RSA: " + decryptedText);





        // Construir firma digital
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey); // Iniciar con la clave privada
        signature.update(data.getBytes()); // Firmar los datos
        byte[] signedData = signature.sign(); // Generar la firma
        String signatureBase64 = Base64.getEncoder().encodeToString(signedData); // Convertir a Base64
        System.out.println("Signature (Base64): " + signatureBase64); // Mostrar firma en Base64

        // Verificar firma digital
        signature.initVerify(publicKey); // Iniciar con la clave pública
        signature.update(data.getBytes()); // Verificar los mismos datos
        boolean isVerified = signature.verify(Base64.getDecoder().decode(signatureBase64)); // Verificar la firma
        System.out.println("Signature Verified: " + isVerified);
    }

    // Convertir clave pública a formato PEM
    public static String convertToPEMPublicKey(PublicKey publicKey) {
        String base64PublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        StringBuilder pem = new StringBuilder();
        pem.append("-----BEGIN PUBLIC KEY-----\n");
        for (int i = 0; i < base64PublicKey.length(); i += 64) {
            pem.append(base64PublicKey, i, Math.min(i + 64, base64PublicKey.length()));
            pem.append("\n");
        }
        pem.append("-----END PUBLIC KEY-----");
        return pem.toString();
    }

    // Convertir clave privada a formato PEM (PKCS#8)
    public static String convertToPEMPrivateKey(PrivateKey privateKey) {
        String base64PrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        StringBuilder pem = new StringBuilder();
        pem.append("-----BEGIN PRIVATE KEY-----\n");
        for (int i = 0; i < base64PrivateKey.length(); i += 64) {
            pem.append(base64PrivateKey, i, Math.min(i + 64, base64PrivateKey.length()));
            pem.append("\n");
        }
        pem.append("-----END PRIVATE KEY-----");
        return pem.toString();
    }
}
