package com.kanionland.charsheet.exp.application.config;

import com.kanionland.charsheet.exp.application.handlers.creation.defaults.BodyRaceTemplateHandler;
import com.kanionland.charsheet.exp.application.handlers.creation.defaults.CharacterDefaultsChain;
import com.kanionland.charsheet.exp.application.handlers.creation.defaults.CharacterInitializeHandler;
import com.kanionland.charsheet.exp.application.handlers.creation.defaults.StylesRaceTemplateHandler;
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
  public CharacterDefaultsChain characterCreationChain() {
    return new CharacterDefaultsChain(
        initializeHandler,
        bodyRaceTemplateHandler,
        stylesRaceTemplateHandler
    );
  }
}
