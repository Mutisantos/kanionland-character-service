package com.kanionland.charsheet.exp.application.handlers.creation.persistence;

import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractRelationsHandler implements CharacterRelationsHandler {

  private CharacterRelationsHandler next;

  @Override
  public CharacterRelationsHandler setNext(CharacterRelationsHandler next) {
    this.next = next;
    return next;
  }

  @Override
  public CharacterEntity handle(CharacterEntity builder, CharacterModel model) {
    CharacterEntity processed = process(builder, model);
    return next != null ? next.handle(processed, model) : processed;
  }

  protected abstract CharacterEntity process(CharacterEntity builder, CharacterModel model);
}
