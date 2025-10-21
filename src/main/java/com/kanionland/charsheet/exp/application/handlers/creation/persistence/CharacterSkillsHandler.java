package com.kanionland.charsheet.exp.application.handlers.creation.persistence;

import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.domain.models.Skill;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.SkillEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.SkillRepository;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class CharacterSkillsHandler extends AbstractRelationsHandler {

  private final SkillRepository skillRepository;

  @Override
  protected CharacterEntity process(CharacterEntity newCharacter, final CharacterModel model) {
    return processSkills(newCharacter, model);
  }

  private CharacterEntity processSkills(CharacterEntity newCharacter,
      final CharacterModel characterModel) {
    if (CollectionUtils.isEmpty(characterModel.getSkills())) {
      return newCharacter;
    }
    Set<String> skillNames = characterModel.getSkills().stream()
        .map(Skill::getName)
        .collect(Collectors.toSet());

    Set<SkillEntity> existingSkills = skillRepository.findByNameIn(skillNames)
        .stream()
        .collect(Collectors.toSet());
    // TODO : log when a skill is not found
    newCharacter.setSkills(existingSkills);
    return newCharacter;
  }


}
