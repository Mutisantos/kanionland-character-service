package com.kanionland.charsheet.exp.application.config;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "characters.templates")
public class CharacterTemplatesProperties {

  @NotNull
  private final Map<RaceEnum, RaceTemplate> races;

  public CharacterTemplatesProperties(Map<RaceEnum, RaceTemplate> races) {
    this.races = Map.copyOf(races);
  }

  public Map<RaceEnum, RaceTemplate> getRaces() {
    return races;
  }
}
