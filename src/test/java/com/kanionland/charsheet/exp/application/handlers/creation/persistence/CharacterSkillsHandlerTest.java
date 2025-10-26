package com.kanionland.charsheet.exp.application.handlers.creation.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.domain.models.Skill;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.SkillEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.SkillRepository;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

@ExtendWith(MockitoExtension.class)
class CharacterSkillsHandlerTest {

  @Mock
  private SkillRepository skillRepository;

  @InjectMocks
  private CharacterSkillsHandler handler;

  @Test
  void whenProcessWithNoSkillsThenReturnCharacterUnchanged() {
    // Given
    CharacterModel model = CharacterModel.builder().build();
    CharacterEntity character = CharacterEntity.builder().build();

    // When
    CharacterEntity result = handler.process(character, model);

    // Then
    assertThat(result).isSameAs(character);
    assertThat(CollectionUtils.isEmpty(result.getSkills())).isTrue();
  }

  @Test
  void whenProcessWithSkillsThenShouldSaveExistingSkills() {
    // Given
    Skill skill1 = Skill.builder().name("Parry").build();
    Skill skill2 = Skill.builder().name("Trolleo Ninja").build();

    SkillEntity skillEntity1 = SkillEntity.builder()
        .id(1L)
        .name("Parry")
        .build();
    SkillEntity skillEntity2 = SkillEntity.builder()
        .id(2L)
        .name("Trolleo Ninja")
        .build();

    CharacterModel model = CharacterModel.builder()
        .skills(Set.of(skill1, skill2))
        .build();

    CharacterEntity character = CharacterEntity.builder().build();

    when(skillRepository.findByNameIn(Set.of("Parry", "Trolleo Ninja")))
        .thenReturn(List.of(skillEntity1));

    // When
    CharacterEntity result = handler.process(character, model);

    // Then
    assertThat(result.getSkills()).hasSize(1);
    assertThat(result.getSkills().iterator().next().getName()).isEqualTo("Parry");
  }

  @Test
  void whenProcessWithNoMatchingSkillsThenReturnEmptySkills() {
    // Given
    Skill skill1 = Skill.builder().name("Nonexistent Skill").build();
    CharacterModel model = CharacterModel.builder()
        .skills(Set.of(skill1))
        .build();
    CharacterEntity character = CharacterEntity.builder().build();

    when(skillRepository.findByNameIn(Set.of("Nonexistent Skill")))
        .thenReturn(List.of());

    // When
    CharacterEntity result = handler.process(character, model);

    // Then
    assertThat(CollectionUtils.isEmpty(result.getSkills())).isTrue();
  }
}
