package com.abia.agialagenda.people.service.api.model;

import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EspecialidadeOutput {
    private TSID id;
    private String nome;

}
