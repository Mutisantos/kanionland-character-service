package com.kanionland.charsheet.exp.application.handlers.creation;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.domain.models.CharacterModel;

public interface CharacterHandler {

  CharacterModel.CharacterModelBuilder handle(CharacterModel.CharacterModelBuilder builder,
      RaceEnum race);

  CharacterHandler setNext(CharacterHandler next);
}
