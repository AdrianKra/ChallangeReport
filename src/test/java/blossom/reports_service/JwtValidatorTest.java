package blossom.reports_service;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import blossom.reports_service.inbound.Security.JwtValidator;
import blossom.reports_service.model.Role;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtValidatorTest {

    @InjectMocks
    private JwtValidator jwtValidator;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private Jws<Claims> jwsClaims;

    @Mock
    private Claims claims;

    private PublicKey publicKey;
    private String validJwt;
    private String userEmail;

    @BeforeEach
    public void setUp() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        validJwt = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhbGV4QGZobXMuZGUiLCJhdXRoIjoiVVNFUiIsImlhdCI6MTcxNjkwMTAxMiwiZXhwIjo0ODcwNTAxMDEyfQ.YgqbpgTBpRSmPMAY1-t9UVXAvIVigEjKSHeM8axslIGVFUozeacYfb3VLPvGJ99RWf3C8q5UjMmhpBEAPlzVgyHUFhj_6NUHkYuTcFFGIvdAClQ00RIIbNhx0g23PrQFo7AZtF1m6BbLe6QQUU12gEH4XYq4hL0p4BV9iNRnSdSHK-vLGuP9jSLFwOQRzMkWEBMfvn6w4mQWlQuBG9Zi6hjg5b4meOHUvQJeVr_sAHq2e15ig292ndisAVbQ1VP6-91-qpmOQ4-O36o_DUUVCaSDV4Gup9lE4Deu0UPdaYTR0UuSmfZx0e3DnQufwKUokqNUwdgzfRnwLIvXekgwAA";
        userEmail = "alex@fhms.de";

        jwtValidator = new JwtValidator();
        jwtValidator.loadSigningKeys();
        publicKey = jwtValidator.getPublicKey();
    }

    @Test
    public void testResolveToken() {
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer " + validJwt);
        assertEquals(validJwt, jwtValidator.resolveToken(httpServletRequest));

        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn(null);
        assertNull(jwtValidator.resolveToken(httpServletRequest));

        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn("InvalidToken");
        assertNull(jwtValidator.resolveToken(httpServletRequest));
    }

    @Test
    public void testIsValidJWT_ValidToken() {
        assertDoesNotThrow(() -> jwtValidator.isValidJWT(validJwt));
    }

    @Test
    public void testGetAuthentication() {
        Authentication authentication = jwtValidator.getAuthentication(validJwt);
        assertNotNull(authentication);
        assertEquals(userEmail, authentication.getName());
    }

    @Test
    public void testGetUserEmail() {
        assertEquals(userEmail, jwtValidator.getUserEmail(validJwt));
    }

    @Test
    public void testGetRoles_UserRole() {
        Collection<GrantedAuthority> roles = jwtValidator.getRoles(validJwt);
        assertTrue(roles.contains(Role.USER));
    }

    @Test
    public void testGetRoles_AdminRole() {
        // TODO: use different jwt with admin role
        Collection<GrantedAuthority> roles = jwtValidator.getRoles(validJwt);
        assertTrue(roles.contains(Role.USER));
    }
}