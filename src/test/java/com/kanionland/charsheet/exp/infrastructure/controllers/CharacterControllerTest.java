package com.kanionland.charsheet.exp.infrastructure.controllers;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanionland.charsheet.exp.application.commands.CreateCharacterCommand;
import com.kanionland.charsheet.exp.application.ports.CharacterCreationPort;
import com.kanionland.charsheet.exp.application.ports.CharacterQueryPort;
import com.kanionland.charsheet.exp.infrastructure.configs.SecurityConfig;
import com.kanionland.charsheet.exp.infrastructure.mappers.CharacterCreationMapper;
import com.kanionland.charsheet.exp.infrastructure.requests.CreateCharacterRequest;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;
import com.kanionland.charsheet.exp.infrastructure.security.JwtTokenProvider;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CharacterController.class)
@ContextConfiguration(classes = {SecurityConfig.class, JwtTokenProvider.class,
    CharacterController.class})
@ActiveProfiles("test")
public class CharacterControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private CharacterCreationPort characterCreationPort;

  @MockitoBean
  private CharacterQueryPort characterQueryPort;

  @MockitoBean
  private CharacterCreationMapper signUpRequestMapper;

  @MockitoBean
  private JwtTokenProvider jwtTokenProvider;


  @Test
  void createCharacterWhenValidInputThenReturns201() throws Exception {
    // Given
    User principal = new User("Beaklimit", "", Collections.emptyList());
    final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
        principal, "", Collections.emptyList());
    CreateCharacterRequest request = CreateCharacterRequest.builder().build();
    when(signUpRequestMapper.toCommand(any(CreateCharacterRequest.class), anyString()))
        .thenReturn(CreateCharacterCommand.builder().build());
    when(characterCreationPort.createCharacter(any(CreateCharacterCommand.class)))
        .thenReturn(CharacterBasicResponse.builder().build());
    when(jwtTokenProvider.getAuthentication(anyString()))
        .thenReturn(authToken);
    // When & Then
    mockMvc.perform(post("/characters")
            .header("Authorization", "Bearer jysdf6ADF94GAew834=")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());
  }


  @Test
  void getAllUserCharactersWhenValidInputThenReturns200() throws Exception {
    // Given
    User principal = new User("Beaklimit", "", Collections.emptyList());
    final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
        principal, "", Collections.emptyList());
    CreateCharacterRequest request = CreateCharacterRequest.builder().build();
    when(signUpRequestMapper.toCommand(any(CreateCharacterRequest.class), anyString()))
        .thenReturn(CreateCharacterCommand.builder().build());
    when(characterQueryPort.getAllUserCharacters("Beaklimit"))
        .thenReturn(List.of(CharacterBasicResponse.builder().build()));
    when(jwtTokenProvider.getAuthentication(anyString()))
        .thenReturn(authToken);
    // When & Then
    mockMvc.perform(get("/characters")
            .header("Authorization", "Bearer jysdf6ADF94GAew834=")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

}
