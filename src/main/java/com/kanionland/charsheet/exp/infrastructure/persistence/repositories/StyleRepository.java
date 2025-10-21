package com.kanionland.charsheet.exp.infrastructure.persistence.repositories;

import com.kanionland.charsheet.exp.infrastructure.persistence.entities.StyleEntity;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StyleRepository extends JpaRepository<StyleEntity, Long> {

  Optional<StyleEntity> findByName(String name);

  @Query("SELECT part FROM StyleEntity part WHERE part.name IN :names")
  List<StyleEntity> findByNameIn(@Param("names") Collection<String> names);
}
