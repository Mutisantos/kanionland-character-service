package com.kanionland.charsheet.exp.application.handlers.creation.defaults;


import com.kanionland.charsheet.exp.application.config.StyleTemplates;
import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.domain.models.Style;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StylesRaceTemplateHandler extends AbstractCharacterHandler {

  private final StyleTemplates styleTemplates;

  @Override
  protected CharacterModel.CharacterModelBuilder process(
      CharacterModel.CharacterModelBuilder builder, RaceEnum race) {
    List<Style> styles = styleTemplates.getTemplates().getOrDefault(race, List.of()).stream()
        .map(styleName -> Style.builder()
            .name(styleName)
            .styleClass(styleName.toLowerCase().replace(" ", "-"))
            .build())
        .collect(Collectors.toList());
    return builder.styles(styles);
  }
}
