package com.abia.agialagenda.people.service.api.model;

import com.abia.agialagenda.people.service.domain.model.Endereco;
import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PacienteOutput {
    private TSID id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private Endereco endereco;

}
