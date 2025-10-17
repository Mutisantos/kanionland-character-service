package com.kanionland.charsheet.exp.application.config;

import com.kanionland.charsheet.exp.application.handlers.creation.BodyRaceTemplateHandler;
import com.kanionland.charsheet.exp.application.handlers.creation.CharacterCreationChain;
import com.kanionland.charsheet.exp.application.handlers.creation.CharacterInitializeHandler;
import com.kanionland.charsheet.exp.application.handlers.creation.StylesRaceTemplateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CharacterCreationHandlerConfig {

  private final CharacterInitializeHandler initializeHandler;
  private final BodyRaceTemplateHandler bodyRaceTemplateHandler;
  private final StylesRaceTemplateHandler stylesRaceTemplateHandler;

  @Bean
  public CharacterCreationChain characterCreationChain() {
    return new CharacterCreationChain(
        initializeHandler,
        bodyRaceTemplateHandler,
        stylesRaceTemplateHandler
    );
  }
}
