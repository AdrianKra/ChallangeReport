package blossom.reports_service.inbound.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import blossom.reports_service.model.Role;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityTime;

    private PrivateKey privateKey;

    @PostConstruct
    protected void loadSigningKeys() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        // read privateKey
        InputStream inputStream = new ClassPathResource("privateKey").getInputStream();
        byte[] encodedPrivateKey = IOUtils.toByteArray(inputStream);

        // encode privateKey
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        this.privateKey = keyFactory.generatePrivate(privateKeySpec);
    }

    public String createJwt(String username, Role role) {
        final Claims claims = Jwts.claims()
                .subject(username)
                .add("auth", role.getAuthority())
                .build();

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityTime);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(validity)
                .signWith(privateKey)
                .compact();
    }
}
