package org.johnwesley.gerenciamentorifa.repository;

import org.johnwesley.gerenciamentorifa.models.Bilhete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BilheteRepository extends JpaRepository<Bilhete, Long> {
    List<Bilhete> findByRifaId(Long id);

    long countByRifaId(Long rifaId);
}
