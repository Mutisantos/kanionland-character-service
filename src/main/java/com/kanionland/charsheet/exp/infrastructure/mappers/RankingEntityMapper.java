package com.kanionland.charsheet.exp.infrastructure.mappers;

import com.kanionland.charsheet.exp.domain.models.Ranking;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.RankingEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RankingEntityMapper {

  Ranking toDomain(RankingEntity entity);

  RankingEntity toEntity(Ranking domain);

}
