package com.abia.agialagenda.people.service.api.model;

import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class MedicoOutput {
    private TSID id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Set<EspecialidadeOutput> especialidades;

}
