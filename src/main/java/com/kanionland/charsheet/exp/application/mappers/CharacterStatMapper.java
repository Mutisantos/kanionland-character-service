package com.kanionland.charsheet.exp.application.mappers;

import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterStatEntity;
import com.kanionland.charsheet.exp.infrastructure.responses.StatResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CharacterStatMapper {

  @Mapping(target = "name", source = "stat.name")
  @Mapping(target = "experience", source = "experience")
  @Mapping(target = "totalLevel", source = "level")
    // TODO Provide methods to correctly calculate total level

  StatResponse toStatResponse(CharacterStatEntity entity);

}
