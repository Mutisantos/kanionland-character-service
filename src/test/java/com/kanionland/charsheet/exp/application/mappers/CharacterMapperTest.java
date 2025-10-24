package com.kanionland.charsheet.exp.application.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterPartEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterStatEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.PartEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.RankingEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.SkillEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.StatEntity;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterPartResponse;
import com.kanionland.charsheet.exp.infrastructure.responses.StatResponse;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CharacterMapperTest {

  private CharacterMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = Mappers.getMapper(CharacterMapper.class);
  }

  @Test
  void testToDomain() {
    // Given
    CharacterEntity entity = createTestCharacterEntity();
    // When
    CharacterModel model = mapper.toDomain(entity);
    // Then
    assertNotNull(model);
    assertEquals(entity.getName(), model.getName());
  }

  @Test
  void testToEntity() {
    // Given
    CharacterModel model = CharacterModel.builder()
        .name("Test Character")
        .build();

    // When
    CharacterEntity entity = mapper.toEntity(model);

    // Then
    assertNotNull(entity);
    assertEquals(model.getName(), entity.getName());
  }

  @Test
  void testToResponse() {
    // Given
    CharacterEntity entity = createTestCharacterEntity();

    // When
    CharacterBasicResponse response = mapper.toResponse(entity);

    // Then
    assertNotNull(response);
    assertEquals(entity.getName(), response.getName());
    assertEquals(entity.getRace().getName(), response.getRace());
    assertEquals(entity.getRanking().getLevel(), response.getCharacterRank());
    assertFalse(response.getSkills().isEmpty());
  }

  @Test
  void testToPartResponse() {
    // Given
    CharacterPartEntity partEntity = CharacterPartEntity.builder()
        .part(PartEntity.builder()
            .name("Warrior")
            .maxHealth(100L)
            .build())
        .currentHealth(85L)
        .build();

    // When
    CharacterPartResponse response = mapper.toPartResponse(partEntity);

    // Then
    assertNotNull(response);
    assertEquals(partEntity.getPart().getName(), response.getName());
    assertEquals(partEntity.getPart().getMaxHealth(), response.getMaxHealth());
    assertEquals(partEntity.getCurrentHealth(), response.getCurrentHealth());
  }

  @Test
  void testToStatResponse() {
    // Given
    CharacterStatEntity statEntity = CharacterStatEntity.builder()
        .stat(StatEntity.builder()
            .id("FUE")
            .name("Fuerza")
            .levelUpExperience(1000L)
            .build())
        .experience(1500L)
        .level(5L)
        .bonus(2L)
        .statLimit(10L)
        .mastery(1L)
        .penalty(2L)
        .build();

    // When
    StatResponse response = mapper.toStatResponse(statEntity);

    // Then
    assertNotNull(response);
    assertEquals(statEntity.getStat().getName(), response.getName());
    assertEquals(statEntity.getExperience(), response.getExperience());
    assertEquals(statEntity.getLevel(), response.getTotalLevel());
  }

  @Test
  void testToDomainList() {
    // Given
    CharacterEntity entity1 = createTestCharacterEntity();
    CharacterEntity entity2 = createTestCharacterEntity();
    entity2.setId(2L);
    entity2.setName("Another Character");

    // When
    List<CharacterModel> models = mapper.toDomainList(List.of(entity1, entity2));

    // Then
    assertNotNull(models);
    assertEquals(2, models.size());
    assertEquals(entity1.getName(), models.get(0).getName());
    assertEquals(entity2.getName(), models.get(1).getName());
  }

  @Test
  void testToEntityList() {
    // Given
    CharacterModel model1 = CharacterModel.builder().race(RaceEnum.KANION).name("Nuzz").build();
    CharacterModel model2 = CharacterModel.builder().race(RaceEnum.KANION).name("Nilin").build();

    // When
    List<CharacterEntity> entities = mapper.toEntityList(List.of(model1, model2));

    // Then
    assertNotNull(entities);
    assertEquals(2, entities.size());
    assertEquals(model1.getName(), entities.get(0).getName());
    assertEquals(model2.getName(), entities.get(1).getName());
  }

  private CharacterEntity createTestCharacterEntity() {
    return CharacterEntity.builder()
        .id(1L)
        .name("Nuzz")
        .race(RaceEnum.KANION)
        .title("Guerrero")
        .gender("Masculino")
        .age(25L)
        .height(80L)
        .weight(40L)
        .thirst(100L)
        .sleep(50L)
        .hunger(50L)
        .ranking(RankingEntity.builder()
            .id(1L)
            .level(1L)
            .description("Infantería")
            .pointsRequired(0L)
            .build())
        .bodyParts(List.of(
            CharacterPartEntity.builder()
                .part(PartEntity.builder()
                    .id(1L)
                    .name("Head")
                    .maxHealth(100L)
                    .build())
                .currentHealth(100L)
                .build()))
        .skills(Set.of(
            SkillEntity.builder()
                .id(1L)
                .name("Parrillear")
                .requiredExperience(4000L)
                .mentalCost(0L)
                .physicalCost(2L)
                .isUpgradable(false)
                .upgradePoints(0L)
                .upgradeLevel(1L)
                .description("Desvía un ataque y deja al rival Vulnerable.")
                .build()))

        .build();
  }
}
