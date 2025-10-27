package com.kanionland.charsheet.exp.infrastructure.controllers;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanionland.charsheet.exp.application.commands.CreateCharacterCommand;
import com.kanionland.charsheet.exp.application.ports.CharacterCreationPort;
import com.kanionland.charsheet.exp.infrastructure.configs.SecurityConfig;
import com.kanionland.charsheet.exp.infrastructure.mappers.CharacterCreationMapper;
import com.kanionland.charsheet.exp.infrastructure.requests.CreateCharacterRequest;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;
import com.kanionland.charsheet.exp.infrastructure.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
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
  private CharacterCreationMapper signUpRequestMapper;


  @Test
  void createCharacterWhenValidInputThenReturns201() throws Exception {
    // Given
    CreateCharacterRequest request = CreateCharacterRequest.builder().build();
    when(signUpRequestMapper.toCommand(any(CreateCharacterRequest.class)))
        .thenReturn(CreateCharacterCommand.builder().build());
    when(characterCreationPort.createCharacter(any(CreateCharacterCommand.class)))
        .thenReturn(CharacterBasicResponse.builder().build());
    // When & Then
    mockMvc.perform(post("/characters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());
  }

}
