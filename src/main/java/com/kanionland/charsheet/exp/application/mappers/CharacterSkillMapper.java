package com.kanionland.charsheet.exp.application.mappers;

import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterStatEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.SkillEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CharacterSkillMapper {

  String toStatResponse(CharacterStatEntity entity);

  @Named("mapSkillToResponse")
  default String mapSkillToResponse(SkillEntity entity) {
    return entity.getName();
  }

}
