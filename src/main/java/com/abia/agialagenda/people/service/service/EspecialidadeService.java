package com.abia.agialagenda.people.service.service;

import com.abia.agialagenda.people.service.domain.model.Especialidade;
import com.abia.agialagenda.people.service.domain.model.EspecialidadeId;
import com.abia.agialagenda.people.service.domain.repository.EspecialidadeRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadeService {

    private final EspecialidadeRepository repo;
    public EspecialidadeService(EspecialidadeRepository repo) { this.repo = repo; }

    public Especialidade criar(Especialidade esp) {
        if (repo.existsByNomeIgnoreCase(esp.getNome())) {
            throw new EntityExistsException("Especialidade já existe");
        }
        return repo.save(esp);
    }

    public Especialidade buscarPorId(EspecialidadeId id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Especialidade não encontrada"));
    }

    public List<Especialidade> listarTodos() { return repo.findAll(); }

    public Especialidade atualizar(EspecialidadeId id, Especialidade dados) {
        Especialidade esp = buscarPorId(id);
        esp.setNome(dados.getNome());
        return repo.save(esp);
    }

    public void excluir(EspecialidadeId id) { repo.deleteById(id); }
}
