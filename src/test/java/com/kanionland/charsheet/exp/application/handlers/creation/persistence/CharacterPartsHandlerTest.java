package com.kanionland.charsheet.exp.application.handlers.creation.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.domain.models.Part;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterPartEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.PartEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.PartRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CharacterPartsHandlerTest {

  @Mock
  private PartRepository partRepository;

  @InjectMocks
  private CharacterPartsHandler handler;

  @Test
  void whenProcessWithNoPartsThenReturnCharacterUnchanged() {
    // Given
    CharacterModel model = CharacterModel.builder()
        .bodyParts(Collections.emptyList())
        .build();
    CharacterEntity character = CharacterEntity.builder().build();

    // When
    CharacterEntity result = handler.process(character, model);

    // Then
    assertThat(result).isSameAs(character);
    assertThat(result.getBodyParts()).isEmpty();
  }

  @Test
  void whenProcessWithPartsThenShouldSaveExistingParts() {
    // Given
    Part part1 = Part.builder().name("Head").build();
    Part part2 = Part.builder().name("Torso").build();

    PartEntity headPart = PartEntity.builder()
        .id(1L)
        .name("Head")
        .build();
    PartEntity torsoPart = PartEntity.builder()
        .id(2L)
        .name("Torso")
        .build();

    CharacterModel model = CharacterModel.builder()
        .bodyParts(List.of(part1, part2))
        .build();

    CharacterEntity character = CharacterEntity.builder().build();

    when(partRepository.findByNameIn(List.of("Head", "Torso")))
        .thenReturn(List.of(headPart, torsoPart));

    // When
    CharacterEntity result = handler.process(character, model);

    // Then
    assertThat(result.getBodyParts()).hasSize(2);
    assertThat(result.getBodyParts().stream()
        .map(CharacterPartEntity::getPart)
        .map(PartEntity::getName))
        .containsExactlyInAnyOrder("Head", "Torso");
  }

  @Test
  void whenProcessWithSomeNonExistingPartsThenShouldSaveOnlyExistingParts() {
    // Given
    Part existingPart = Part.builder().name("Head").build();
    Part nonExistingPart = Part.builder().name("Nonexistent Part").build();
    PartEntity headPart = PartEntity.builder()
        .id(1L)
        .name("Head")
        .build();

    CharacterModel model = CharacterModel.builder()
        .bodyParts(List.of(existingPart, nonExistingPart))
        .build();

    CharacterEntity character = CharacterEntity.builder().build();

    when(partRepository.findByNameIn(List.of("Head", "Nonexistent Part")))
        .thenReturn(List.of(headPart));

    // When
    CharacterEntity result = handler.process(character, model);

    // Then
    assertThat(result.getBodyParts()).hasSize(1);
    assertThat(result.getBodyParts().get(0).getPart().getName()).isEqualTo("Head");
  }
}
