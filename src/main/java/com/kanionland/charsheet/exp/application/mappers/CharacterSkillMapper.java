package com.kanionland.charsheet.exp.application.mappers;

import com.kanionland.charsheet.exp.infrastructure.persistence.entities.SkillEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CharacterSkillMapper {

  @Named("mapSkillToResponse")
  default String mapSkillToResponse(SkillEntity entity) {
    return entity.getName();
  }

}
