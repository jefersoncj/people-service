package com.abia.agialagenda.people.service.api.controller;

import com.abia.agialagenda.people.service.api.model.PacienteInput;
import com.abia.agialagenda.people.service.api.model.PacienteOutput;
import com.abia.agialagenda.people.service.commom.IdGenerator;
import com.abia.agialagenda.people.service.domain.model.Paciente;
import com.abia.agialagenda.people.service.domain.model.PacienteId;
import com.abia.agialagenda.people.service.domain.repository.PacienteRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;



@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteRepository pacienteRepository;

    @GetMapping
    public Page<PacienteOutput> search(@PageableDefault Pageable pageable) {
        Page<Paciente> pacientes = pacienteRepository.findAll(pageable);
        return pacientes.map(this::convertToModel);
    }


    @GetMapping("{pacienteId}")
    public PacienteOutput getOne(@PathVariable TSID pacienteId){
        Paciente paciente = pacienteRepository.findById( new PacienteId(pacienteId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return convertToModel(paciente);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PacienteOutput criar(@RequestBody PacienteInput input) {
        Paciente paciente = Paciente.builder()
                .id(new PacienteId(IdGenerator.generateTSID()))
                .nome(input.getNome())
                .cpf(input.getCpf())
                .dataNascimento(input.getDataNascimento())
                .email(input.getEmail())
                .telefone(input.getTelefone())
                .endereco(input.getEndereco())
                .build();
        paciente = pacienteRepository.saveAndFlush(paciente);
        return convertToModel(paciente);
    }

    @PutMapping("/{pacienteId}")
    public PacienteOutput update(@PathVariable TSID pacienteId,
                               @RequestBody PacienteInput input) {
        Paciente paciente = pacienteRepository.findById(new PacienteId(pacienteId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        paciente.setNome(input.getNome());
        paciente.setCpf(input.getCpf());
        paciente.setDataNascimento(input.getDataNascimento());
        paciente.setEmail(input.getEmail());
        paciente.setTelefone(input.getTelefone());
        paciente.setEndereco(input.getEndereco());
        paciente = pacienteRepository.save(paciente);

        return convertToModel(paciente);
    }

    @DeleteMapping("/{pacienteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable TSID pacienteId) {
        Paciente paciente  = pacienteRepository.findById(new PacienteId(pacienteId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        pacienteRepository.delete(paciente);
    }

    @DeleteMapping("/{pacienteId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable(@PathVariable TSID pacienteId) {
        Paciente paciente  = pacienteRepository.findById(new PacienteId(pacienteId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        paciente.setAtivo(false);
        pacienteRepository.save(paciente);
    }

    private PacienteOutput convertToModel(Paciente paciente) {
        return PacienteOutput.builder()
                .id(paciente.getId().getValue())
                .nome(paciente.getNome())
                .cpf(paciente.getCpf())
                .dataNascimento(paciente.getDataNascimento())
                .email(paciente.getEmail())
                .telefone(paciente.getTelefone())
                .endereco(paciente.getEndereco())
                .build();
    }

}
