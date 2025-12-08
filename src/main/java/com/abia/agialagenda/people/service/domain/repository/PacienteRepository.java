package com.abia.agialagenda.people.service.domain.repository;

import com.abia.agialagenda.people.service.domain.model.Paciente;
import com.abia.agialagenda.people.service.domain.model.PacienteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, PacienteId> {
}
