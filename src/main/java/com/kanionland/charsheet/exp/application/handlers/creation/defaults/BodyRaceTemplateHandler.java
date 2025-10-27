package com.kanionland.charsheet.exp.application.handlers.creation.defaults;


import com.kanionland.charsheet.exp.application.configs.BodyTemplates;
import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.domain.models.Part;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BodyRaceTemplateHandler extends AbstractCharacterHandler {

  private final BodyTemplates raceBodyTemplates;

  @Override
  protected CharacterModel.CharacterModelBuilder process(
      CharacterModel.CharacterModelBuilder builder, RaceEnum race) {
    List<Part> bodyParts = raceBodyTemplates.getTemplates().getOrDefault(race, List.of()).stream()
        .map(partName -> Part.builder()
            .name(partName)
            .maxHealth(100L)
            .build())
        .collect(Collectors.toList());

    return builder.bodyParts(bodyParts);
  }
}
