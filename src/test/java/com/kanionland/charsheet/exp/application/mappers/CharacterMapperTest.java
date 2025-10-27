package com.kanionland.charsheet.exp.application.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.infrastructure.mappers.RankingEntityMapperImpl;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterPartEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterStatEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.PartEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.RankingEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.SkillEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.StatEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.StyleEntity;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterPartResponse;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CharacterSkillMapperImpl.class, CharacterStatMapperImpl.class,
    RankingEntityMapperImpl.class, CharacterMapperImpl.class})
class CharacterMapperTest {

  @Autowired
  private CharacterMapper mapper;

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
  void testToDomainWhenEntityIsNullThenReturnsNull() {
    // When
    CharacterModel model = mapper.toDomain(null);
    // Then
    assertNull(model);
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
  void testToEntityWhenEntityIsNullThenReturnsNull() {
    // When
    CharacterEntity entity = mapper.toEntity(null);
    // Then
    assertNull(entity);
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
  void testToResponseWithNullParametersShouldReturnWithDefaultValues() {
    // Given
    CharacterEntity entity = createNullValuedCharacterEntity();
    // When
    CharacterBasicResponse response = mapper.toResponse(entity);
    // Then
    assertNotNull(response);
    assertEquals(0L, response.getCharacterRank());
    assertEquals(0L, response.getAge());
    assertEquals(0L, response.getWeight());
    assertEquals(0L, response.getHeight());
    assertEquals(0L, response.getThirst());
    assertEquals(0L, response.getHunger());
    assertEquals(0L, response.getSleep());
  }

  @Test
  void testToResponseWhenEntityIsNullThenReturnsNull() {
    // When
    CharacterBasicResponse response = mapper.toResponse(null);
    // Then
    assertNull(response);
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
  void testToPartResponseWhenEntityIsNullThenReturnsNull() {
    // When
    CharacterPartResponse response = mapper.toPartResponse(null);
    // Then
    assertNull(response);
  }

  @Test
  void testToPartResponseWithNullParametersShouldReturnWithDefaultValues() {
    // Given
    CharacterPartEntity partEntity = CharacterPartEntity.builder()
        .part(PartEntity.builder()
            .name("Warrior")
            .maxHealth(null)
            .build())
        .currentHealth(null)
        .build();
    // When
    CharacterPartResponse response = mapper.toPartResponse(partEntity);
    // Then
    assertNotNull(response);
    assertEquals(response.getName(), "Warrior");
    assertEquals(response.getMaxHealth(), 0L);
    assertEquals(response.getCurrentHealth(), 0L);
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
  void testToDomainListWhenListIsNullThenReturnsNull() {
    // When
    List<CharacterModel> models = mapper.toDomainList(null);
    // Then
    assertNull(models);
  }

  @Test
  void testToDomainListWhenListIsEmptyThenReturnsEmptyList() {
    // When
    List<CharacterModel> models = mapper.toDomainList(Collections.emptyList());
    // Then
    assertThat(models).isNotNull().isEmpty();
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

  @Test
  void testToEntityListWhenListIsNullThenReturnsNull() {
    // When
    List<CharacterEntity> entities = mapper.toEntityList(null);
    // Then
    assertNull(entities);
  }

  @Test
  void testToEntityListWhenListIsEmptyThenReturnsEmptyList() {
    // When
    List<CharacterEntity> entities = mapper.toEntityList(Collections.emptyList());
    // Then
    assertNotNull(entities);
    assertThat(entities).isEmpty();
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
        .styles(Set.of(
            StyleEntity.builder()
                .id(1L)
                .name("Artista Marcial")
                .styleClass("MARTIAL")
                .build()))
        .stats(Set.of(
            CharacterStatEntity.builder()
                .id(1L)
                .level(5L)
                .experience(2500L)
                .stat(StatEntity.builder()
                    .id("FUE")
                    .name("Fuerza")
                    .levelUpExperience(1000L)
                    .build())
                .penalty(2L)
                .mastery(1l)
                .bonus(2L)
                .statLimit(10L)
                .build()
        ))

        .build();
  }

  private CharacterEntity createNullValuedCharacterEntity() {
    return CharacterEntity.builder()
        .id(null)
        .name(null)
        .race(RaceEnum.KANION)
        .title(null)
        .gender(null)
        .age(null)
        .height(null)
        .weight(null)
        .thirst(null)
        .sleep(null)
        .hunger(null)
        .ranking(null)
        .bodyParts(null)
        .skills(null)
        .styles(null)
        .stats(null)
        .build();
  }
}
