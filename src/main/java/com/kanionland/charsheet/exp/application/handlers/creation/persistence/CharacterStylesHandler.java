package com.kanionland.charsheet.exp.application.handlers.creation.persistence;

import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.domain.models.CharacterStyle;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.StyleEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.StyleRepository;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterStylesHandler extends AbstractRelationsHandler {

  private final StyleRepository styleRepository;

  @Override
  protected CharacterEntity process(CharacterEntity newCharacter, final CharacterModel model) {
    return processStyles(newCharacter, model);
  }

  private CharacterEntity processStyles(CharacterEntity newCharacter,
      final CharacterModel characterModel) {
    Set<String> styleNames = characterModel.getStyles().stream()
        .map(CharacterStyle::getName)
        .collect(Collectors.toSet());

    Set<StyleEntity> existingSkills = styleRepository.findByNameIn(styleNames)
        .stream()
        .collect(Collectors.toSet());
    // TODO : log when a skill is not found
    newCharacter.setStyles(existingSkills);
    return newCharacter;
  }

}
