package com.kanionland.charsheet.exp.application.handlers.creation.defaults;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.kanionland.charsheet.exp.application.configs.BodyTemplates;
import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BodyRaceTemplateHandlerTest {

  @Mock
  private BodyTemplates bodyTemplates;

  private BodyRaceTemplateHandler handler;

  @BeforeEach
  void setUp() {
    handler = new BodyRaceTemplateHandler(bodyTemplates);
  }

  @Test
  void whenProcessThenShouldAddBodyPartsFromTemplate() {
    // Given
    when(bodyTemplates.getTemplates())
        .thenReturn(Map.of(RaceEnum.KANION, List.of("Head", "Torso", "Arm")));
    var builder = CharacterModel.builder()
        .name("Test Character")
        .race(RaceEnum.KANION);
    // When
    var result = handler.process(builder, RaceEnum.KANION);
    var character = result.build();
    // Then
    assertThat(character.getBodyParts()).hasSize(3);
    assertThat(character.getBodyParts())
        .extracting("name")
        .containsExactlyInAnyOrder("Head", "Torso", "Arm");

    character.getBodyParts().forEach(part ->
        assertThat(part.getMaxHealth()).isEqualTo(100L)
    );
  }

  @Test
  void whenProcessAndNoTemplateFoundThenShouldNotAddBodyParts() {
    // Given
    when(bodyTemplates.getTemplates()).thenReturn(Map.of());
    var builder = CharacterModel.builder()
        .name("Test Character")
        .race(RaceEnum.KANION);
    // When
    var result = handler.process(builder, RaceEnum.KANION);
    var character = result.build();
    // Then
    assertThat(character.getBodyParts()).isEmpty();
  }

}
