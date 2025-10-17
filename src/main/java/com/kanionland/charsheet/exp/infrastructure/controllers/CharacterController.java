package com.kanionland.charsheet.exp.infrastructure.controllers;

import com.kanionland.charsheet.exp.application.commands.CreateCharacterCommand;
import com.kanionland.charsheet.exp.application.ports.CharacterCreationPort;
import com.kanionland.charsheet.exp.infrastructure.mappers.CharacterCreationMapper;
import com.kanionland.charsheet.exp.infrastructure.requests.CreateCharacterRequest;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

  private final CharacterCreationPort characterCreationPort;
  private final CharacterCreationMapper characterCreationMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CharacterBasicResponse> createCharacter(
      @Valid @RequestBody CreateCharacterRequest request) {
    final CreateCharacterCommand creationCommand = characterCreationMapper.toCommand(request);
    final CharacterBasicResponse character = characterCreationPort.createCharacter(creationCommand);
    return ResponseEntity.ok(character);
  }


}
