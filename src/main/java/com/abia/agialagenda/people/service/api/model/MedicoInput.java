package com.abia.agialagenda.people.service.api.model;

import com.abia.agialagenda.people.service.domain.model.EspecialidadeId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class MedicoInput {

    @NotBlank
    @Size(max = 150)
    private String nome;

    @NotBlank
    @Size(min = 11, max = 14)
    private String cpf;

    @Email
    private String email;

    private String telefone;

    private List<EspecialidadeId> especialidadeIds;

}
