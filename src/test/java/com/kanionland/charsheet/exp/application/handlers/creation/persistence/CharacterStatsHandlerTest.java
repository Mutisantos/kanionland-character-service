package com.kanionland.charsheet.exp.application.handlers.creation.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.domain.models.CharacterModel;
import com.kanionland.charsheet.exp.domain.models.CharacterStat;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.StatEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.StatRaceEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.StatRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CharacterStatsHandlerTest {

  @Mock
  private StatRepository statRepository;

  @InjectMocks
  private CharacterStatsHandler handler;

  @Test
  void whenProcessAndStatsExistThenShouldSaveStats() {
    //Given
    CharacterStat characterStatFue = CharacterStat.builder().name("FUE")
        .build();
    CharacterStat characterStatDef = CharacterStat.builder().name("DEF")
        .build();
    CharacterStat characterStatUnknown = CharacterStat.builder().name("UNK")
        .build();
    StatEntity statEntityFue = StatEntity.builder().id("FUE").name("Fuerza").build();
    StatEntity statEntityDef = StatEntity.builder().id("DEF").name("Defensa").build();
    StatRaceEntity kanionFueConstraint = StatRaceEntity.builder().race(RaceEnum.KANION)
        .stat(statEntityFue).build();
    StatRaceEntity kanionDefConstraint = StatRaceEntity.builder().race(RaceEnum.KANION)
        .stat(statEntityFue).build();
    statEntityFue.setRaceConstraints(Set.of(kanionFueConstraint));
    statEntityDef.setRaceConstraints(Set.of(kanionDefConstraint));

    CharacterModel model = CharacterModel.builder()
        .stats(Set.of(characterStatFue, characterStatDef, characterStatUnknown))
        .race(RaceEnum.KANION)
        .build();
    CharacterEntity onBuildCharacter = CharacterEntity.builder().race(RaceEnum.KANION).build();
    when(statRepository.findByAbbreviationIn(Set.of("FUE", "DEF", "UNK")))
        .thenReturn(List.of(statEntityFue, statEntityDef));
    //When
    final CharacterEntity process = handler.process(onBuildCharacter, model);
    //Then
    assertThat(process.getStats()).hasSize(2);
    final List<String> stats = process.getStats().stream()
        .map(stat -> stat.getStat().getId())
        .sorted()
        .collect(Collectors.toList());
    assertThat(stats.get(0)).isEqualTo("DEF");
    assertThat(stats.get(1)).isEqualTo("FUE");
  }

}
