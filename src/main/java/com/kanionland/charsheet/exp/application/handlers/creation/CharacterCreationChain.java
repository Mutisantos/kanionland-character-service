package com.kanionland.charsheet.exp.application.handlers.creation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterCreationChain {

  private final CharacterInitializeHandler initializeHandler;
  private final BodyRaceTemplateHandler bodyRaceTemplateHandler;
  private final StylesRaceTemplateHandler stylesRaceTemplateHandler;

  public CharacterHandler buildChain() {
    initializeHandler
        .setNext(bodyRaceTemplateHandler)
        .setNext(stylesRaceTemplateHandler);
    return initializeHandler;
  }
}