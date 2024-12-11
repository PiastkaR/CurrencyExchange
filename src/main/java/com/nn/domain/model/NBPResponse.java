package com.nn.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NBPResponse(String table, String currency, String codes, List<Rate> rates) implements Serializable {

    @Serial
    private static final long serialVersionUID = -3866504751211379983L;

}
