package com.kanionland.charsheet.exp.infrastructure.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

  @Mock
  private JwtTokenProvider tokenProvider;

  @InjectMocks
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @BeforeEach
  void setUp() {
    SecurityContextHolder.clearContext();
  }

  @Test
  void testDoFilterInternalValidToken() throws ServletException, IOException {
    // Given
    String token = "valid.token.here";
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", "Bearer " + token);
    MockHttpServletResponse response = new MockHttpServletResponse();
    FilterChain filterChain = mock(FilterChain.class);
    Authentication authentication = new TestingAuthenticationToken("testUser", null, "ROLE_USER");
    when(tokenProvider.validateToken(token)).thenReturn(true);
    when(tokenProvider.getAuthentication(token)).thenReturn(authentication);
    // When
    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
    // Then
    verify(tokenProvider).validateToken(token);
    verify(tokenProvider).getAuthentication(token);
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    assertNotNull(auth, "Authentication should not be null");
    assertEquals("testUser", auth.getName());
    verify(filterChain).doFilter(any(ServletRequest.class), any(ServletResponse.class));
  }

  @Test
  void testDoFilterInternalInvalidToken() throws ServletException, IOException {
    // Given
    String token = "invalid.token.here";
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", "Bearer " + token);
    MockHttpServletResponse response = new MockHttpServletResponse();
    FilterChain filterChain = new MockFilterChain();
    when(tokenProvider.validateToken(token)).thenReturn(false);
    // When
    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
    // Then
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    assertNull(auth);
  }

  @Test
  void testDoFilterInternalNullToken() throws ServletException, IOException {
    // Given
    String token = "invalid.token.here";
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    FilterChain filterChain = new MockFilterChain();
    // When
    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
    // Then
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    assertNull(auth);
  }
}