package com.kanionland.charsheet.exp.application.handlers.creation.persistence;

import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.domain.models.CharacterStat;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterStatEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.StatEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.StatRaceEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.StatRepository;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterStatsHandler extends AbstractRelationsHandler {

  private final StatRepository statRepository;

  @Override
  protected CharacterEntity process(CharacterEntity newCharacter, final CharacterModel model) {
    return processStats(newCharacter, model);
  }

  private CharacterEntity processStats(CharacterEntity newCharacter,
      final CharacterModel characterModel) {
    Set<String> stats = characterModel.getStats().stream()
        .map(CharacterStat::getName)
        .collect(Collectors.toSet());

    Map<String, StatEntity> existingStats = statRepository.findByNameIn(stats)
        .stream()
        .collect(Collectors.toMap(StatEntity::getName, Function.identity()));

    List<CharacterStatEntity> characterStats = new LinkedList<>();
    for (CharacterStat stat : characterModel.getStats()) {
      if (existingStats.containsKey(stat.getName())) {
        final StatEntity statEntity = existingStats.get(stat.getName());
        final Optional<StatRaceEntity> statRaceConstraint = statEntity.getRaceConstraints()
            .stream()
            .filter(raceConstraint -> raceConstraint.getRace().equals(characterModel.getRace()))
            .findFirst();

        CharacterStatEntity characterStat = new CharacterStatEntity(
            newCharacter,
            statEntity,
            stat.getLevel(),
            stat.getExperience(),
            statRaceConstraint.map(StatRaceEntity::getRaceLimit).orElse(stat.getLimit())
        );
        characterStats.add(characterStat);
      }
      // TODO log when a stat is not found as a WARN
    }
    newCharacter.setStats(characterStats);
    return newCharacter;
  }


}
