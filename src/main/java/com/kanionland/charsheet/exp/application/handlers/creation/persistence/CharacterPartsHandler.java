package com.kanionland.charsheet.exp.application.handlers.creation.persistence;

import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.domain.models.Part;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterPartEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.PartEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.PartRepository;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterPartsHandler extends AbstractRelationsHandler {

  private final PartRepository partRepository;

  @Override
  protected CharacterEntity process(CharacterEntity newCharacter, final CharacterModel model) {
    return processParts(newCharacter, model);
  }

  private CharacterEntity processParts(CharacterEntity newCharacter,
      final CharacterModel characterModel) {
    List<String> partNames = characterModel.getBodyParts().stream()
        .map(Part::getName)
        .collect(Collectors.toList());

    Map<String, PartEntity> existingParts = partRepository.findByNameIn(partNames)
        .stream()
        .collect(Collectors.toMap(PartEntity::getName, Function.identity()));

    List<CharacterPartEntity> characterParts = new LinkedList<>();
    for (Part part : characterModel.getBodyParts()) {
      if (existingParts.containsKey(part.getName())) {

        CharacterPartEntity characterPart = new CharacterPartEntity(newCharacter,
            existingParts.get(part.getName()));
        characterParts.add(characterPart);
      }
      // TODO add logging when a part is not found
    }
    newCharacter.setBodyParts(characterParts);
    return newCharacter;
  }


}
