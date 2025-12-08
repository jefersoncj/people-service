package com.abia.agialagenda.people.service.domain.repository;

import com.abia.agialagenda.people.service.domain.model.Medico;
import com.abia.agialagenda.people.service.domain.model.MedicoId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, MedicoId> {

    Optional<Medico> findByCpf(String cpf);
    boolean existsByCpf(String cpf);

    @Query(
            value = "SELECT DISTINCT m FROM Medico m LEFT JOIN FETCH m.especialidades",
            countQuery = "SELECT COUNT(m) FROM Medico m"
    )
    Page<Medico> findAllWithEspecialidades(Pageable pageable);
}
