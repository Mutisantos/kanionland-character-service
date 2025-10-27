package com.kanionland.charsheet.exp.infrastructure.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

  private final String jwtSecret = "testSecretKey12345678901234567890123456789012";
  private JwtTokenProvider tokenProvider;

  @BeforeEach
  void setUp() {
    tokenProvider = new JwtTokenProvider();
    ReflectionTestUtils.setField(tokenProvider, "jwtSecret", jwtSecret);
  }

  @Test
  void testValidateTokenValidToken() {
    // Given
    String token = Jwts.builder()
        .setSubject("testuser")
        .setIssuedAt(new Date())
        .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
        .compact();
    // When
    boolean isValid = tokenProvider.validateToken(token);
    // Then
    assertTrue(isValid);
  }

  @Test
  @SneakyThrows
  void testValidateTokenWhenInvalidValidTokenThenReturnsFalse() {
    // Given
    String token = Jwts.builder()
        .setSubject("testuser")
        .setIssuedAt(new Date())
        .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
        .compact();
    // When
    boolean isValid = tokenProvider.validateToken(null);
    // Then
    assertFalse(isValid);
  }

  @Test
  void testGetAuthenticationWhenValidTokenThenReturnsAuthentication() {
    // Given
    String token = Jwts.builder()
        .setSubject("testuser")
        .setIssuedAt(new Date())
        .setClaims(Map.of("role", "USER", "sub", "testuser"))
        .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
        .compact();
    // When
    Authentication authentication = tokenProvider.getAuthentication(token);
    // Then
    assertNotNull(authentication);
    assertEquals("testuser", authentication.getName());
  }

}