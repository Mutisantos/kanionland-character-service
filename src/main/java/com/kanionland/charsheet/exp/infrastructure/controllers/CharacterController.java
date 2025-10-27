package com.kanionland.charsheet.exp.infrastructure.controllers;

import com.kanionland.charsheet.exp.application.commands.CreateCharacterCommand;
import com.kanionland.charsheet.exp.application.ports.CharacterCreationPort;
import com.kanionland.charsheet.exp.infrastructure.mappers.CharacterCreationMapper;
import com.kanionland.charsheet.exp.infrastructure.requests.CreateCharacterRequest;
import com.kanionland.charsheet.exp.infrastructure.responses.CharacterBasicResponse;
import com.kanionland.charsheet.exp.infrastructure.security.JwtTokenProvider;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

  private final CharacterCreationPort characterCreationPort;
  private final CharacterCreationMapper characterCreationMapper;
  private final JwtTokenProvider jwtTokenProvider;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CharacterBasicResponse> createCharacter(
      @RequestHeader("Authorization") String authHeader,
      @Valid @RequestBody CreateCharacterRequest request) {
    final String owner = retrieveUserFromAuth(authHeader);
    final CreateCharacterCommand creationCommand = characterCreationMapper.toCommand(request,
        owner);
    final CharacterBasicResponse character = characterCreationPort.createCharacter(creationCommand);
    return ResponseEntity.created(URI.create("/characters/" + character.getName())).body(character);
  }

  private String retrieveUserFromAuth(String authToken) {
    String token = authToken.substring(7); // Remove "Bearer " prefix
    String username = jwtTokenProvider.getAuthentication(token).getName();
    return username;
  }


}
