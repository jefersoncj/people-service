package com.abia.agialagenda.people.service.api.controller;

import com.abia.agialagenda.people.service.api.model.EspecialidadeOutput;
import com.abia.agialagenda.people.service.api.model.MedicoInput;
import com.abia.agialagenda.people.service.api.model.MedicoOutput;
import com.abia.agialagenda.people.service.commom.IdGenerator;
import com.abia.agialagenda.people.service.domain.model.Especialidade;
import com.abia.agialagenda.people.service.domain.model.EspecialidadeId;
import com.abia.agialagenda.people.service.domain.model.Medico;
import com.abia.agialagenda.people.service.domain.model.MedicoId;
import com.abia.agialagenda.people.service.domain.repository.MedicoRepository;
import com.abia.agialagenda.people.service.service.MedicoService;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoRepository medicoRepository;
    private final MedicoService service;

    @GetMapping
    public Page<MedicoOutput> search(@PageableDefault Pageable pageable) {
        Page<Medico> medicos = medicoRepository.findAllWithEspecialidades(pageable);
        return medicos.map(this::convertToModel);
    }


    @GetMapping("{medicoId}")
    public MedicoOutput getOne(@PathVariable TSID medicoId){
        Medico medico = medicoRepository.findById( new MedicoId(medicoId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return convertToModel(medico);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicoOutput criar(@RequestBody MedicoInput input) {
        Medico medico = Medico.builder()
                .id(new MedicoId(IdGenerator.generateTSID()))
                .nome(input.getNome())
                .cpf(input.getCpf())
                .email(input.getEmail())
                .telefone(input.getTelefone())
                .build();

        List<EspecialidadeId> ids = (List<EspecialidadeId>) input.getEspecialidadeIds();


        Set<EspecialidadeId> especialidadeIds = null;
        if (ids != null) {
            especialidadeIds = new java.util.HashSet<>();
            for (EspecialidadeId i : ids) especialidadeIds.add(i);
        }

        Medico saved = service.criarMedico(medico, especialidadeIds);
        return convertToModel(saved);
    }

    @PutMapping("/{medicoId}")
    public Medico update(@PathVariable TSID medicoId,
                               @RequestBody MedicoInput input) {
        Medico medico = medicoRepository.findById(new MedicoId(medicoId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        medico.setNome(input.getNome());
        medico.setCpf(input.getCpf());
        medico.setEmail(input.getEmail());
        medico.setTelefone(input.getTelefone());

        List<EspecialidadeId> ids = (List<EspecialidadeId>) input.getEspecialidadeIds();
        Set<EspecialidadeId> especialidadeIds = null;
        if (ids != null) {
            especialidadeIds = new java.util.HashSet<>();
            for (EspecialidadeId i : ids) especialidadeIds.add(i);
        }
        return service.atualizarMedico(medico.getId(), medico, especialidadeIds);
    }

    @DeleteMapping("/{medicoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable TSID medicoId) {
        Medico medico = medicoRepository.findById(new MedicoId(medicoId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        service.excluirMedico(medico.getId());
    }
//    @DeleteMapping("/{medicoId}/enable")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void disable(@PathVariable TSID medicoId) {
//        Medico medico  = medicoRepository.findById(new MedicoId(medicoId))
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        medico.setAtivo(false);
//        medicoRepository.save(medico);
//    }

    private MedicoOutput convertToModel(Medico medico) {
        return MedicoOutput.builder()
                .id(medico.getId().getValue())
                .nome(medico.getNome())
                .email(medico.getEmail())
                .telefone(medico.getTelefone())
                .especialidades(
                        convertEspecialidadesToModel(
                                new HashSet<>(medico.getEspecialidades())
                        )
                )
                .build();
    }
    private Set<EspecialidadeOutput> convertEspecialidadesToModel(Set<Especialidade> especialidades) {
        return especialidades.stream()
                .map(esp -> EspecialidadeOutput.builder()
                        .id(esp.getId().getValue())
                        .nome(esp.getNome())
                        .build()
                )
                .collect(Collectors.toSet());
    }
}
