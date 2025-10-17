package com.kanionland.charsheet.exp.infrastructure.mappers;

import com.kanionland.charsheet.exp.application.commands.CreateCharacterCommand;
import com.kanionland.charsheet.exp.infrastructure.requests.CreateCharacterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CharacterCreationMapper {

  CreateCharacterCommand toCommand(CreateCharacterRequest request);

}
