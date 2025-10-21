package com.kanionland.charsheet.exp.application.handlers.creation.persistence;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterRelationsChain {

  private final CharacterPartsHandler partsHandler;
  private final CharacterStatsHandler statsHandler;
  private final CharacterStylesHandler stylesHandler;
  private final CharacterSkillsHandler skillsHandler;

  @PostConstruct
  public CharacterRelationsHandler buildChain() {
    partsHandler
        .setNext(statsHandler)
        .setNext(stylesHandler)
        .setNext(skillsHandler);
    return partsHandler;
  }

  public CharacterRelationsHandler getInitialChainHandler() {
    return partsHandler;
  }
}