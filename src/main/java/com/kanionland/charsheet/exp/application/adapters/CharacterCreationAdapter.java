package com.kanionland.charsheet.exp.application.adapters;

import com.kanionland.charsheet.exp.application.commands.CreateCharacterCommand;
import com.kanionland.charsheet.exp.application.handlers.creation.defaults.CharacterDefaultsChain;
import com.kanionland.charsheet.exp.application.handlers.creation.persistence.CharacterRelationsChain;
import com.kanionland.charsheet.exp.application.mappers.CharacterMapper;
import com.kanionland.charsheet.exp.application.ports.CharacterCreationPort;
import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.domain.models.CharacterStat;
import com.kanionland.charsheet.exp.domain.models.CharacterStyle;
import com.kanionland.charsheet.exp.domain.models.Part;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.CharacterRepository;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterCreationAdapter implements CharacterCreationPort {

  private final CharacterRepository characterRepository;
  private final CharacterMapper mapper;
  private final CharacterDefaultsChain characterDefaultsChain;
  private final CharacterRelationsChain characterRelationsChain;

  @Override
  public CharacterBasicResponse createCharacter(CreateCharacterCommand creationCommand) {
    CharacterModel.CharacterModelBuilder builder = buildCharacterFromCommand(creationCommand);
    CharacterModel character = characterDefaultsChain.getInitialChainHandler()
        .handle(builder, creationCommand.getRace())
        .build();
    CharacterEntity entity = mapper.toEntity(character);
    entity = characterRelationsChain.getInitialChainHandler().handle(entity, character);

    CharacterEntity savedEntity = characterRepository.save(entity);
    return mapper.toResponse(savedEntity);
  }


  private CharacterModel.CharacterModelBuilder buildCharacterFromCommand(
      CreateCharacterCommand creationCommand) {
    return CharacterModel.builder()
        .name(creationCommand.getName())
        .race(creationCommand.getRace())
        .title(creationCommand.getTitle())
        .gender(creationCommand.getGender())
        .age(creationCommand.getAge())
        .height(creationCommand.getHeight())
        .weight(creationCommand.getWeight())
        .bodyParts(creationCommand.getBodyParts().stream()
            .map(part -> Part.builder().name(part).build())
            .collect(Collectors.toList()))
        .styles(creationCommand.getStyles().stream()
            .map(style -> CharacterStyle.builder().name(style).build())
            .collect(Collectors.toList()))
        .stats(creationCommand.getStats().stream()
            .map(stat -> CharacterStat.builder()
                .name(stat.getName())
                .level(stat.getInitialExp())
                .experience(stat.getInitialExp())
                .limit(stat.getInitialLimit())
                .build())
            .collect(Collectors.toList()))
        ;
  }

}
