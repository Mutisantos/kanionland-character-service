package com.kanionland.charsheet.exp.application.handlers.creation.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.domain.models.CharacterStyle;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.StyleEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.StyleRepository;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

@ExtendWith(MockitoExtension.class)
class CharacterStylesHandlerTest {

  @Mock
  private StyleRepository styleRepository;

  @InjectMocks
  private CharacterStylesHandler handler;

  @Test
  void whenProcessWithNoStylesThenReturnCharacterUnchanged() {
    // Given
    CharacterModel model = CharacterModel.builder()
        .styles(Collections.emptySet())
        .build();
    CharacterEntity character = CharacterEntity.builder().build();

    // When
    CharacterEntity result = handler.process(character, model);

    // Then
    assertThat(result).isSameAs(character);
    assertThat(CollectionUtils.isEmpty(result.getStyles())).isTrue();
  }

  @Test
  void whenProcessWithStylesThenShouldSaveExistingStyles() {
    // Given
    CharacterStyle style1 = CharacterStyle.builder().name("Warrior").build();
    CharacterStyle style2 = CharacterStyle.builder().name("Mage").build();

    StyleEntity styleEntity1 = StyleEntity.builder()
        .name("Warrior")
        .styleClass("warrior")
        .id(1L)
        .build();

    StyleEntity styleEntity2 = StyleEntity.builder()
        .name("Mage")
        .styleClass("warrior")
        .id(2L)
        .build();

    CharacterModel model = CharacterModel.builder()
        .styles(Set.of(style1, style2))
        .build();

    CharacterEntity character = CharacterEntity.builder().build();

    when(styleRepository.findByNameIn(Set.of("Warrior", "Mage")))
        .thenReturn(List.of(styleEntity1, styleEntity2));

    // When
    CharacterEntity result = handler.process(character, model);

    // Then
    assertThat(result.getStyles()).hasSize(2);
    assertThat(result.getStyles().stream().map(StyleEntity::getName))
        .containsExactlyInAnyOrder("Warrior", "Mage");
  }

  @Test
  void whenProcessWithNoMatchingStylesThenReturnEmptyStyles() {
    // Given
    CharacterStyle style = CharacterStyle.builder().name("Nonexistent Style").build();
    CharacterModel model = CharacterModel.builder()
        .styles(Set.of(style))
        .build();
    CharacterEntity character = CharacterEntity.builder().build();

    when(styleRepository.findByNameIn(Set.of("Nonexistent Style")))
        .thenReturn(Collections.emptyList());

    // When
    CharacterEntity result = handler.process(character, model);

    // Then
    assertThat(CollectionUtils.isEmpty(result.getStyles())).isTrue();
  }
}
