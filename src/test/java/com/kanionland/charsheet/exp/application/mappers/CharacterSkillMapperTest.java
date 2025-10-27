package com.kanionland.charsheet.exp.application.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.kanionland.charsheet.exp.infrastructure.persistence.entities.SkillEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CharacterSkillMapperTest {

  private CharacterSkillMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = Mappers.getMapper(CharacterSkillMapper.class);
  }

  @Test
  void testToStatResponse() {
    // Given
    SkillEntity skillEntity = SkillEntity.builder()
        .id(1L)
        .name("Parry")
        .description("Deflects attack")
        .requiredExperience(1000L)
        .physicalCost(2L)
        .mentalCost(2L)
        .isUpgradable(false)
        .upgradeLevel(0L)
        .upgradePoints(0L)
        .build();
    // When
    final String skillResponse = mapper.mapSkillToResponse(skillEntity);
    // Then
    assertThat(skillResponse).isNotNull().isEqualTo("Parry");
  }

}
