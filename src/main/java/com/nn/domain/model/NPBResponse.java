package com.nn.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serial;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NPBResponse(String table, String currency, String codes, Rate[] rates) implements Serializable {

    @Serial
    private static final long serialVersionUID = -3866504751211379983L;


}
