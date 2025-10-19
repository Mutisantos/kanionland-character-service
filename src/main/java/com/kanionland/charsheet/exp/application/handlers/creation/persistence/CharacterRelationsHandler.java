package com.kanionland.charsheet.exp.application.handlers.creation.persistence;

import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;

public interface CharacterRelationsHandler {

  CharacterEntity handle(CharacterEntity builder, CharacterModel model);

  CharacterRelationsHandler setNext(CharacterRelationsHandler next);
}
