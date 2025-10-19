package com.kanionland.charsheet.exp.application.handlers.creation.defaults;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.domain.models.CharacterModel;

public interface CharacterDefaultsHandler {

  CharacterModel.CharacterModelBuilder handle(CharacterModel.CharacterModelBuilder builder,
      RaceEnum race);

  CharacterDefaultsHandler setNext(CharacterDefaultsHandler next);
}
