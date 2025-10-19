package com.kanionland.charsheet.exp.application.handlers.creation.defaults;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractCharacterHandler implements CharacterDefaultsHandler {

  private CharacterDefaultsHandler next;

  @Override
  public CharacterDefaultsHandler setNext(CharacterDefaultsHandler next) {
    this.next = next;
    return next;
  }

  @Override
  public CharacterModel.CharacterModelBuilder handle(CharacterModel.CharacterModelBuilder builder,
      RaceEnum race) {
    CharacterModel.CharacterModelBuilder processed = process(builder, race);
    return next != null ? next.handle(processed, race) : processed;
  }

  protected abstract CharacterModel.CharacterModelBuilder process(
      CharacterModel.CharacterModelBuilder builder, RaceEnum race);
}
