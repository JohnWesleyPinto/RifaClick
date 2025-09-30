package org.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RifaDTO {
    private String nome;
    private String premio;
    private Integer quantidadeBilhetes;
    private BigDecimal precoBilhete;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
}
