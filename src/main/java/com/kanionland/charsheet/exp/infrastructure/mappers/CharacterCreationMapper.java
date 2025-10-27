package com.kanionland.charsheet.exp.infrastructure.mappers;

import com.kanionland.charsheet.exp.application.commands.CreateCharacterCommand;
import com.kanionland.charsheet.exp.infrastructure.requests.CreateCharacterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CharacterCreationMapper {

  default CreateCharacterCommand toCommand(CreateCharacterRequest request, String owner) {
    if (request == null) {
      return null;
    }
    return mapToCommand(request, owner);
  }

  @Mapping(target = "owner", source = "owner")
  CreateCharacterCommand mapToCommand(CreateCharacterRequest request, String owner);

}
