package com.kanionland.charsheet.exp.infrastructure.security;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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
  void testValidateToken_ValidToken() {
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
}