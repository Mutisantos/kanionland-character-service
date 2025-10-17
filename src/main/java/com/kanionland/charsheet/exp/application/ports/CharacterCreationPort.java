package com.kanionland.charsheet.exp.application.ports;

import com.kanionland.charsheet.exp.application.commands.CreateCharacterCommand;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;

public interface CharacterCreationPort {

  CharacterBasicResponse createCharacter(CreateCharacterCommand creationCommand);
}
