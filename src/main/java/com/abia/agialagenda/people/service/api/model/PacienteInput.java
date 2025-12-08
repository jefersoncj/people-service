package com.abia.agialagenda.people.service.api.model;

import com.abia.agialagenda.people.service.domain.model.Endereco;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PacienteInput {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 150)
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11)
    private String cpf;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    @Email(message = "Email inválido")
    private String email;

    @Size(max = 15)
    private String telefone;

    @Embedded
    private Endereco endereco;

}
