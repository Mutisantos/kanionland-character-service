package com.kanionland.charsheet.exp.infrastructure.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.kanionland.charsheet.exp.domain.models.Ranking;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.RankingEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RankingEntityMapperTest {

  @InjectMocks
  private RankingEntityMapperImpl mapper;

  @Test
  void testToDomain() {
    // Given
    RankingEntity entity = RankingEntity.builder()
        .id(1L)
        .level(5L)
        .description("Bronce")
        .pointsRequired(1000L)
        .build();

    // When
    Ranking domain = mapper.toDomain(entity);

    // Then
    assertNotNull(domain);
    assertEquals(entity.getLevel(), domain.getLevel());
    assertEquals(entity.getPointsRequired(), domain.getPointsRequired());
    assertEquals(entity.getDescription(), domain.getDescription());
  }

  @Test
  void testToEntity() {
    // Given
    Ranking domain = Ranking.builder()
        .level(5L)
        .description("Bronce")
        .pointsRequired(1000L)
        .build();

    // When
    RankingEntity entity = mapper.toEntity(domain);

    // Then
    assertNotNull(entity);
    assertEquals(domain.getLevel(), entity.getLevel());
    assertEquals(domain.getPointsRequired(), entity.getPointsRequired());
    assertEquals(domain.getDescription(), entity.getDescription());
  }

}