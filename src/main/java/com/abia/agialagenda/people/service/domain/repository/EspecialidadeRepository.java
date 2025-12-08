package com.abia.agialagenda.people.service.domain.repository;

import com.abia.agialagenda.people.service.domain.model.Especialidade;
import com.abia.agialagenda.people.service.domain.model.EspecialidadeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, EspecialidadeId> {

    Optional<Especialidade> findByNomeIgnoreCase(String nome);
    boolean existsByNomeIgnoreCase(String nome);
}
