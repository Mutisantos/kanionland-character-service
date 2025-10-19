package com.kanionland.charsheet.exp.application.adapters;

import com.kanionland.charsheet.exp.application.commands.CreateCharacterCommand;
import com.kanionland.charsheet.exp.application.handlers.creation.defaults.CharacterDefaultsChain;
import com.kanionland.charsheet.exp.application.mappers.CharacterMapper;
import com.kanionland.charsheet.exp.application.ports.CharacterCreationPort;
import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.CharacterRepository;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.PartRepository;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterCreationAdapter implements CharacterCreationPort {

  private final CharacterRepository characterRepository;
  private final PartRepository partRepository;
  private final CharacterMapper mapper;
  private final CharacterDefaultsChain characterDefaultsChain;

  @Override
  public CharacterBasicResponse createCharacter(CreateCharacterCommand creationCommand) {
    CharacterModel.CharacterModelBuilder builder = buildCharacter(creationCommand);
    CharacterModel character = characterDefaultsChain.getInitialChainHandler()
        .handle(builder, creationCommand.getRace())
        .build();
    CharacterEntity entity = mapper.toEntity(character);

    CharacterEntity savedEntity = characterRepository.save(entity);
    return mapper.toResponse(savedEntity);
  }


  private CharacterModel.CharacterModelBuilder buildCharacter(
      CreateCharacterCommand creationCommand) {
    return CharacterModel.builder()
        .name(creationCommand.getName())
        .race(creationCommand.getRace())
        .title(creationCommand.getTitle())
        .gender(creationCommand.getGender())
        .age(creationCommand.getAge())
        .height(creationCommand.getHeight())
        .weight(creationCommand.getWeight());
  }

}
