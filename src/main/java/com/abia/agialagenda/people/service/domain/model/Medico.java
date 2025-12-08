package com.abia.agialagenda.people.service.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Medico {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "BIGINT"))
    private MedicoId id;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String nome;

    @NotBlank
    @Size(min = 11, max = 14)
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Email
    @Column(length = 150)
    private String email;

    @Column(length = 20)
    private String telefone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "medico_especialidade",
            joinColumns = @JoinColumn(name = "medico_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidade_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"medico_id", "especialidade_id"})
    )
    private Set<Especialidade> especialidades = new HashSet<>();
}
