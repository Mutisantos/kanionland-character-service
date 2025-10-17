package com.kanionland.charsheet.exp.application.config;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import java.util.List;
import java.util.Map;
import lombok.Value;

@Value
public class BodyTemplates {

  Map<RaceEnum, List<String>> templates;
}
