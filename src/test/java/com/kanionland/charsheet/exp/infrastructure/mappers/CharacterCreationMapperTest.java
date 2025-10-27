package com.kanionland.charsheet.exp.infrastructure.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.kanionland.charsheet.exp.application.commands.CreateCharacterCommand;
import com.kanionland.charsheet.exp.infrastructure.requests.CreateCharacterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CharacterCreationMapperTest {

  private CharacterCreationMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new CharacterCreationMapperImpl();
  }

  @Test
  void testToCommand() {
    // Given
    CreateCharacterRequest request = CreateCharacterRequest.builder()
        .name("Nuzz")
        .race("KANION")
        .build();

    // When
    CreateCharacterCommand command = mapper.toCommand(request, "muti");

    // Then
    assertNotNull(command);
    assertEquals(request.getName(), command.getName());
    assertEquals(request.getRace(), command.getRace().name());
    assertEquals("muti", command.getOwner());
  }

  @Test
  void testToCommandWhenRequestIsNull() {
    // When
    CreateCharacterCommand command = mapper.toCommand(null, "muti");

    // Then
    assertNull(command);
  }
}