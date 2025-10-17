package com.kanionland.charsheet.exp.application.config;

import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CharacterTemplatesProperties.class)
@RequiredArgsConstructor
public class CharacterTemplateConfig {

  @Bean
  public BodyTemplates bodyTemplates(CharacterTemplatesProperties properties) {
    return new BodyTemplates(properties.getRaces().entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            e -> e.getValue().getBodyParts()
        )));
  }

  @Bean
  public StyleTemplates styleTemplates(CharacterTemplatesProperties properties) {
    return new StyleTemplates(properties.getRaces().entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            e -> e.getValue().getStyles()
        )));
  }
}
