package blossom.reports_service.inbound.Security;

import io.jsonwebtoken.Jwts;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;

public class SigningKeyGenerator {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPair keyPair = Jwts.SIG.RS256.keyPair().build(); // Never use weak algorithms like RS256 in production!
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
        FileOutputStream fos = new FileOutputStream("publicKey");
        fos.write(x509EncodedKeySpec.getEncoded());
        fos.close();

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        fos = new FileOutputStream("privateKey");
        fos.write(pkcs8EncodedKeySpec.getEncoded());
        fos.close();

        System.out.println("Key pair successfully generated.");
    }
}
