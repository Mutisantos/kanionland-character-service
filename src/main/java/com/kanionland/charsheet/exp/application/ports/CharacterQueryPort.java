package com.kanionland.charsheet.exp.application.ports;

import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;
import java.util.List;

public interface CharacterQueryPort {

  List<CharacterBasicResponse> getAllUserCharacters(String owner);
}
