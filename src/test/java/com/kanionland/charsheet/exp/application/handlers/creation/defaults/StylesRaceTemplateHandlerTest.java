package com.kanionland.charsheet.exp.application.handlers.creation.defaults;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.kanionland.charsheet.exp.application.configs.StyleTemplates;
import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.domain.models.CharacterStyle;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StylesRaceTemplateHandlerTest {

  private static final String CHARACTER_NAME = "Nuzz";
  @Mock
  private StyleTemplates styleTemplates;
  private StylesRaceTemplateHandler handler;

  @BeforeEach
  void setUp() {
    handler = new StylesRaceTemplateHandler(styleTemplates);
  }

  @Test
  void whenProcessThenShouldAddStylesFromTemplate() {
    // Given
    when(styleTemplates.getTemplates())
        .thenReturn(Map.of(RaceEnum.KANION, List.of("Warrior", "Mage")));
    var builder = CharacterModel.builder()
        .name(CHARACTER_NAME)
        .race(RaceEnum.KANION)
        .styles(Collections.emptyList());
    // When
    var result = handler.process(builder, RaceEnum.KANION);
    var character = result.build();
    // Then
    assertThat(character.getStyles()).hasSize(2);
    assertThat(character.getStyles())
        .extracting(CharacterStyle::getName)
        .containsExactlyInAnyOrder("Warrior", "Mage");
    assertThat(character.getStyles())
        .extracting(CharacterStyle::getStyleClass)
        .containsExactlyInAnyOrder("warrior", "mage");
  }

  @Test
  void whenProcessAndNoTemplateFoundThenShouldNotAddStyles() {
    // Given
    when(styleTemplates.getTemplates()).thenReturn(Map.of());
    var builder = CharacterModel.builder()
        .name(CHARACTER_NAME)
        .race(RaceEnum.KANION);
    // When
    var result = handler.process(builder, RaceEnum.KANION);
    var character = result.build();
    // Then
    assertThat(character.getStyles()).isNull();
  }

  @Test
  void whenProcessAndRaceNotInTemplateThenShouldNotAddStyles() {
    // Given
    when(styleTemplates.getTemplates()).thenReturn(Collections.emptyMap());
    var builder = CharacterModel.builder()
        .name(CHARACTER_NAME)
        .race(RaceEnum.KANION)
        .styles(Collections.emptyList());
    // When
    var result = handler.process(builder, RaceEnum.KANION);
    var character = result.build();
    // Then
    assertThat(character.getStyles()).isEmpty();
  }

  @Test
  void whenProcessShouldNotDuplicateExistingStyles() {
    // Given
    when(styleTemplates.getTemplates())
        .thenReturn(Map.of(RaceEnum.KANION, List.of("Warrior", "Mage")));
    var existingStyle = CharacterStyle.builder()
        .name("Warrior")
        .styleClass("custom-warrior")
        .build();
    var builder = CharacterModel.builder()
        .name(CHARACTER_NAME)
        .race(RaceEnum.KANION)
        .styles(List.of(existingStyle));
    // When
    var result = handler.process(builder, RaceEnum.KANION);
    var character = result.build();
    // Then
    assertThat(character.getStyles()).hasSize(2);
    assertThat(character.getStyles())
        .filteredOn(style -> style.getName().equals("Warrior"))
        .first()
        .extracting(CharacterStyle::getStyleClass)
        .isEqualTo("custom-warrior");
    assertThat(character.getStyles())
        .extracting(CharacterStyle::getName)
        .containsExactlyInAnyOrder("Warrior", "Mage");
  }
}
