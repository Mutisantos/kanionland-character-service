package com.kanionland.charsheet.exp.application.mappers;

import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.infrastructure.mappers.RankingEntityMapper;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterPartEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterStatEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.SkillEntity;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterPartResponse;
import com.kanionland.charsheet.exp.infrastructure.responses.StatResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {RankingEntityMapper.class})
public interface CharacterMapper {

  CharacterModel toDomain(CharacterEntity entity);

  CharacterEntity toEntity(CharacterModel domain);

  List<CharacterModel> toDomainList(List<CharacterEntity> entities);

  List<CharacterEntity> toEntityList(List<CharacterModel> domains);

  @Mapping(target = "race", source = "race.name")
  @Mapping(target = "characterRank", source = "ranking.level")
  @Mapping(target = "skills", source = "skills", qualifiedByName = "mapSkillToResponse")
  CharacterBasicResponse toResponse(CharacterEntity entity);


  @Mapping(target = "name", source = "part.name")
  @Mapping(target = "currentHealth", source = "currentHealth")
  @Mapping(target = "maxHealth", source = "part.maxHealth")
  CharacterPartResponse toPartResponse(CharacterPartEntity entity);

  @Named("mapSkillToResponse")
  default String mapSkillToResponse(SkillEntity entity) {
    return entity.getName();
  }

  @Mapping(target = "name", source = "stat.name")
  @Mapping(target = "experience", source = "experience")
  @Mapping(target = "totalLevel", source = "level")
    // TODO Provide methods to correctly calculate total level
  StatResponse toStatResponse(CharacterStatEntity entity);

}
