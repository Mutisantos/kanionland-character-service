package com.kanionland.charsheet.exp.infrastructure.persistence.repositories;

import com.kanionland.charsheet.exp.infrastructure.persistence.entities.CharacterEntity;
import com.kanionland.charsheet.exp.infrastructure.persistence.entities.PartEntity;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<CharacterEntity, Long> {

  Optional<PartEntity> findByName(String name);

  @Query("SELECT part FROM PartEntity part WHERE part.name IN :names")
  List<PartEntity> findByNameIn(@Param("names") Collection<String> names);
}
