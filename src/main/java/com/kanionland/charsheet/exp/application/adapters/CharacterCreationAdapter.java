package com.kanionland.charsheet.exp.application.adapters;

import com.kanionland.charsheet.exp.application.commands.CreateCharacterCommand;
import com.kanionland.charsheet.exp.application.handlers.creation.CharacterCreationChain;
import com.kanionland.charsheet.exp.application.ports.CharacterCreationPort;
import com.kanionland.charsheet.exp.infrastructure.mappers.CharacterEntityMapper;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.CharacterRepository;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterCreationAdapter implements CharacterCreationPort {

  private final CharacterRepository characterRepository;
  private final CharacterEntityMapper characterEntityMapper;
  private final CharacterCreationChain characterCreationChain;

  @Override
  public CharacterBasicResponse createCharacter(CreateCharacterCommand creationCommand) {
    return null;
  }

}
