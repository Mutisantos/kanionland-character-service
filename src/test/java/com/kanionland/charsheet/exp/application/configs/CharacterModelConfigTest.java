package com.kanionland.charsheet.exp.application.configs;

import static org.assertj.core.api.Assertions.assertThat;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CharacterModelConfigTest {

  @Autowired
  private CharacterTemplatesProperties properties;

  @Test
  void shouldLoadTemplates() {
    assertThat(properties.getRaces()).containsKey(RaceEnum.KANION);
    assertThat(properties.getRaces()).containsKey(RaceEnum.ZI_BUM);
    assertThat(properties.getRaces()).containsKey(RaceEnum.ORTHYS);
    assertThat(properties.getRaces()).containsKey(RaceEnum.PUMIBLU);
    assertThat(properties.getRaces()).containsKey(RaceEnum.FIERCY);
    assertThat(properties.getRaces()).containsKey(RaceEnum.LENTILLANO);
    assertThat(properties.getRaces()).containsKey(RaceEnum.EYESHADE);
    assertThat(properties.getRaces()).containsKey(RaceEnum.LUIN);
    assertThat(properties.getRaces()).containsKey(RaceEnum.XYRION);
    assertThat(properties.getRaces()).containsKey(RaceEnum.CETARA);
    assertThat(properties.getRaces()).containsKey(RaceEnum.DENDROLIAN);
    assertThat(properties.getRaces()).containsKey(RaceEnum.KEYUI);
    assertThat(properties.getRaces()).containsKey(RaceEnum.UNTOLD);
    assertThat(properties.getRaces()).containsKey(RaceEnum.SKETCHE);
    assertThat(properties.getRaces()).containsKey(RaceEnum.PRIMAL);
    assertThat(properties.getRaces()).containsKey(RaceEnum.ROBOT);
    assertThat(properties.getRaces()).containsKey(RaceEnum.OTHER);

    final RaceTemplate raceTemplate = properties.getRaces().get(RaceEnum.ZI_BUM);
    assertThat(raceTemplate.getBodyParts()).contains("Head");
    assertThat(raceTemplate.getBodyParts()).contains("Left Arm");
    assertThat(raceTemplate.getBodyParts()).contains("Right Arm");
    assertThat(raceTemplate.getBodyParts()).contains("Left Leg");
    assertThat(raceTemplate.getBodyParts()).contains("Right Leg");
    assertThat(raceTemplate.getStyles()).contains("Tirador");
  }

}
