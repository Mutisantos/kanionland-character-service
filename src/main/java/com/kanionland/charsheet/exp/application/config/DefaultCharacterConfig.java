package com.kanionland.charsheet.exp.application.config;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CharacterTemplatesProperties.class)
@RequiredArgsConstructor
public class DefaultCharacterConfig {

  private final CharacterTemplatesProperties templatesProperties;

  @Bean
  public Map<RaceEnum, List<String>> raceBodyTemplates() {
    return templatesProperties.getRaces().entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            entry -> entry.getValue().getBodyParts()
        ));
  }

  @Bean
  public Map<RaceEnum, List<String>> raceSkillTemplates() {
    return templatesProperties.getRaces().entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            entry -> entry.getValue().getStyles()
        ));
  }
}
