package com.abia.agialagenda.people.service.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Paciente {
    @Id
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "BIGINT"))
    private PacienteId id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 150)
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11)
    @Column(unique = true)
    private String cpf;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    @Email(message = "Email inválido")
    private String email;

    @Size(max = 15)
    private String telefone;

    @Embedded
    private Endereco endereco;

    @Builder.Default
    private Boolean ativo = true;
}
