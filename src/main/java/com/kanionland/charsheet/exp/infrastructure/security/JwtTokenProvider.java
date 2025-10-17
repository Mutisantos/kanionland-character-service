package com.kanionland.charsheet.exp.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  @Value("${jwt.secret}")
  private String jwtSecret;

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(getSigningKey())
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Authentication getAuthentication(String token) {
    Claims claims = extractAllClaims(token);
    String username = claims.getSubject();
    List<SimpleGrantedAuthority> authorities = List.of(
        new SimpleGrantedAuthority((String) claims.get("role")));

    User principal = new User(username, "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSigningKey() {
    byte[] keyBytes = this.jwtSecret.getBytes();
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
