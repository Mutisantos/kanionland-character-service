package com.kanionland.charsheet.exp.infrastructure.repositories;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {

  Optional<Character> findByName(String name);

  List<Character> findAllByRace(RaceEnum race);
}
