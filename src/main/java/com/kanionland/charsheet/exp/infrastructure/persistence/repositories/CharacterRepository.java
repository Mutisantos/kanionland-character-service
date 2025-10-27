package com.kanionland.charsheet.exp.infrastructure.persistence.repositories;

import com.kanionland.charsheet.exp.domain.enums.RaceEnum;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {

  Optional<CharacterEntity> findByName(String name);

  List<CharacterEntity> findAllByRace(RaceEnum race);

  List<CharacterEntity> findAllByOwner(String owner);
}
