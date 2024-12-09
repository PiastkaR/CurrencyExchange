package com.nn.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serial;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Rate(String no, String effectiveDate, String mid) implements Serializable {

    @Serial
    private static final long serialVersionUID = 2116309166082083744L;

}
