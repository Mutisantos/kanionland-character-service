package com.kanionland.charsheet.exp.application.handlers.creation.defaults;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterDefaultsChain {

  private final CharacterInitializeHandler initializeHandler;
  private final BodyRaceTemplateHandler bodyRaceTemplateHandler;
  private final StylesRaceTemplateHandler stylesRaceTemplateHandler;

  @PostConstruct
  public CharacterDefaultsHandler buildChain() {
    initializeHandler
        .setNext(bodyRaceTemplateHandler)
        .setNext(stylesRaceTemplateHandler);
    return initializeHandler;
  }

  public CharacterDefaultsHandler getInitialChainHandler() {
    return initializeHandler;
  }
}