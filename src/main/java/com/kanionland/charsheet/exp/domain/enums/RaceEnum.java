package com.kanionland.charsheet.exp.domain.enums;

import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RaceEnum {

  KANION("Kanion"),
  ORTHYS("Orthys"),
  PUMIBLU("Pumiblu"),
  FIERCY("Fiercy"),
  ZI_BUM("Zi-Bum"),
  LENTILLANO("Lentillano"),
  EYESHADE("Eyeshade"),
  LUIN("Luin"),
  XYRION("Xyrion"),
  CETARA("Cetara"),
  DENDROLIAN("Dendrolian"),
  KEYUI("Keyui"),
  UNTOLD("Untold"),
  SKETCHE("Sketche"),
  PRIMAL("Primal"),
  ROBOT("Robot"),
  OTHER("Other");

  private String name;

  public static RaceEnum findByName(final String name) {
    return Stream.of(RaceEnum.values())
        .filter(raceEnum -> raceEnum.name().equals(name))
        .findFirst()
        .orElse(null);
  }

}
