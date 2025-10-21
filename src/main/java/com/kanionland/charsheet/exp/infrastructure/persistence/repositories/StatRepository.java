package com.kanionland.charsheet.exp.infrastructure.persistence.repositories;

import com.kanionland.charsheet.exp.infrastructure.persistence.entities.StatEntity;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository extends JpaRepository<StatEntity, Long> {

  Optional<StatEntity> findByName(String name);

  @Query("SELECT part FROM StatEntity part WHERE part.name IN :names")
  List<StatEntity> findByNameIn(@Param("names") Collection<String> names);
}
