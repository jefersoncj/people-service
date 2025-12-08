package com.abia.agialagenda.people.service.domain.model;


import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class MedicoId implements Serializable {

    private TSID value;

    public MedicoId(TSID value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public MedicoId(Long value) {
        Objects.requireNonNull(value);
        this.value = TSID.from(value);
    }

    public MedicoId(String value) {
        Objects.requireNonNull(value);
        this.value = TSID.from(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}