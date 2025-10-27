package com.kanionland.charsheet.exp.application.adapters;

import com.kanionland.charsheet.exp.application.mappers.CharacterMapper;
import com.kanionland.charsheet.exp.application.ports.CharacterQueryPort;
import com.kanionland.charsheet.exp.infrastructure.persistence.repositories.CharacterRepository;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterQueryAdapter implements CharacterQueryPort {

  private final CharacterRepository characterRepository;
  private final CharacterMapper mapper;


  @Override
  public List<CharacterBasicResponse> getAllUserCharacters(final String owner) {
    return characterRepository.findAllByOwner(owner).stream().map(mapper::toResponse).toList();
  }
}
