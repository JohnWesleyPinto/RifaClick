package org.repository;

import org.models.Rifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RifaRepository extends JpaRepository<Rifa, Long> {
    List<Rifa> findByStatusSorteioTrue();
}
