package com.abia.agialagenda.people.service.service;

import com.abia.agialagenda.people.service.domain.model.Especialidade;
import com.abia.agialagenda.people.service.domain.model.EspecialidadeId;
import com.abia.agialagenda.people.service.domain.model.Medico;
import com.abia.agialagenda.people.service.domain.model.MedicoId;
import com.abia.agialagenda.people.service.domain.repository.EspecialidadeRepository;
import com.abia.agialagenda.people.service.domain.repository.MedicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MedicoService {
    private final MedicoRepository medicoRepo;
    private final EspecialidadeRepository espRepo;


    public MedicoService(MedicoRepository medicoRepo, EspecialidadeRepository espRepo) {
        this.medicoRepo = medicoRepo;
        this.espRepo = espRepo;
    }

    @Transactional
    public Medico criarMedico(Medico medico, Set<EspecialidadeId> especialidadeIds) {
        if (medicoRepo.existsByCpf(medico.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        if (especialidadeIds != null && !especialidadeIds.isEmpty()) {
            Set<Especialidade> espSet = new HashSet<>();
            for (EspecialidadeId id : especialidadeIds) {
                Especialidade esp = espRepo.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Especialidade não encontrada: " + id));
                espSet.add(esp);
            }
            medico.setEspecialidades(espSet);
        }
        return medicoRepo.save(medico);
    }

    public Medico buscarPorId(MedicoId id) {
        return medicoRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Médico não encontrado"));
    }

    @Transactional
    public Medico atualizarMedico(MedicoId id, Medico dados, Set<EspecialidadeId> especialidadeIds) {
        Medico medico = buscarPorId(id);
        medico.setNome(dados.getNome());
        medico.setEmail(dados.getEmail());
        medico.setTelefone(dados.getTelefone());
        // cpf normalmente não se altera; se alterar, validar uniqueness
        if (!medico.getCpf().equals(dados.getCpf())) {
            if (medicoRepo.existsByCpf(dados.getCpf())) {
                throw new IllegalArgumentException("CPF já cadastrado por outro médico");
            }
            medico.setCpf(dados.getCpf());
        }
        if (especialidadeIds != null) {
            Set<Especialidade> espSet = new HashSet<>();
            for (EspecialidadeId espId : especialidadeIds) {
                espSet.add(espRepo.findById(espId).orElseThrow(() -> new EntityNotFoundException("Especialidade não encontrada: " + espId)));
            }
            medico.setEspecialidades(espSet);
        }
        return medicoRepo.save(medico);
    }

    public void excluirMedico(MedicoId id) {
        medicoRepo.deleteById(id);
    }

    public List<Medico> listarTodos() {
        return medicoRepo.findAll();
    }
}
