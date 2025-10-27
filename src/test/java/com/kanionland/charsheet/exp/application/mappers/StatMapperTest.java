package com.kanionland.charsheet.exp.application.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterStatEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.StatEntity;
import com.kanionland.charsheet.exp.infrastructure.responses.StatResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class StatMapperTest {

  private CharacterStatMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = Mappers.getMapper(CharacterStatMapper.class);
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
  void testToStatResponseWithNullParametersShouldReturnWithDefaultValues() {
    // Given
    CharacterStatEntity statEntity = CharacterStatEntity.builder()
        .stat(StatEntity.builder()
            .id(null)
            .name(null)
            .levelUpExperience(null)
            .build())
        .experience(null)
        .level(null)
        .bonus(null)
        .statLimit(null)
        .mastery(null)
        .penalty(null)
        .build();
    // When
    StatResponse response = mapper.toStatResponse(statEntity);
    // Then
    assertNotNull(response);
    assertEquals(statEntity.getStat().getName(), response.getName());
    assertEquals(0L, response.getExperience());
    assertEquals(0L, response.getTotalLevel());
  }

  @Test
  void testToStatResponseWithNullStatShouldReturnWithDefaultValues() {
    // Given
    CharacterStatEntity statEntity = CharacterStatEntity.builder()
        .stat(null)
        .experience(null)
        .level(null)
        .bonus(null)
        .statLimit(null)
        .mastery(null)
        .penalty(null)
        .build();
    // When
    StatResponse response = mapper.toStatResponse(statEntity);
    // Then
    assertNotNull(response);
    assertNull(response.getName());
    assertEquals(0L, response.getExperience());
    assertEquals(0L, response.getTotalLevel());
  }

  @Test
  void testToStatResponseWithNullEntityShouldReturnNull() {
    // When
    StatResponse response = mapper.toStatResponse(null);
    // Then
    assertNull(response);
  }
}
