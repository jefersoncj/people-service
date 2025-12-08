package com.abia.agialagenda.people.service.api.controller;

import com.abia.agialagenda.people.service.api.model.EspecialidadeInput;
import com.abia.agialagenda.people.service.api.model.EspecialidadeOutput;
import com.abia.agialagenda.people.service.commom.IdGenerator;
import com.abia.agialagenda.people.service.domain.model.Especialidade;
import com.abia.agialagenda.people.service.domain.model.EspecialidadeId;
import com.abia.agialagenda.people.service.domain.repository.EspecialidadeRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
public class EspecialidadeController {

    private final EspecialidadeRepository especialidadeRepository;

    @GetMapping
    public Page<EspecialidadeOutput> search(@PageableDefault Pageable pageable) {
        Page<Especialidade> especialidades = especialidadeRepository.findAll(pageable);
        return especialidades.map(this::convertToModel);
    }


    @GetMapping("{especialidadeId}")
    public EspecialidadeOutput getOne(@PathVariable TSID especialidadeId){
        Especialidade especialidade = especialidadeRepository.findById( new EspecialidadeId(especialidadeId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return convertToModel(especialidade);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EspecialidadeOutput criar(@RequestBody EspecialidadeInput input) {
        Especialidade especialidade = Especialidade.builder()
                .id(new EspecialidadeId(IdGenerator.generateTSID()))
                .nome(input.getNome())
                .build();
        especialidade = especialidadeRepository.saveAndFlush(especialidade);
        return convertToModel(especialidade);
    }

    @PutMapping("/{especialidadeId}")
    public EspecialidadeOutput update(@PathVariable TSID especialidadeId,
                               @RequestBody EspecialidadeInput input) {
        Especialidade especialidade = especialidadeRepository.findById(new EspecialidadeId(especialidadeId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        especialidade.setNome(input.getNome());
        especialidade = especialidadeRepository.save(especialidade);

        return convertToModel(especialidade);
    }

    @DeleteMapping("/{especialidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable TSID especialidadeId) {
        Especialidade especialidade  = especialidadeRepository.findById(new EspecialidadeId(especialidadeId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        especialidadeRepository.delete(especialidade);
    }

//    @DeleteMapping("/{especialidadeId}/enable")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void disable(@PathVariable TSID especialidadeId) {
//        Especialidade especialidade  = especialidadeRepository.findById(new EspecialidadeId(especialidadeId))
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        especialidade.setAtivo(false);
//        especialidadeRepository.save(especialidade);
//    }

    private EspecialidadeOutput convertToModel(Especialidade especialidade) {
        return EspecialidadeOutput.builder()
                .id(especialidade.getId().getValue())
                .nome(especialidade.getNome())
                .build();
    }

}
