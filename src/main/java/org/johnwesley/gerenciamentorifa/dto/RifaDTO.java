package org.johnwesley.gerenciamentorifa.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RifaDTO {

    @NotBlank(message = "O nome da rifa é obrigatório.")
    private String nome;

    @NotBlank(message = "O prêmio da rifa é obrigatório.")
    private String premio;

    @NotNull(message = "A quantidade de bilhetes é obrigatória.")
    @Positive(message = "A quantidade de bilhetes deve ser maior que zero.")
    private Integer quantidadeBilhetes;

    @NotNull(message = "O preço do bilhete é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço do bilhete deve ser maior que zero.")
    private BigDecimal precoBilhete;

    @NotNull(message = "A data de início é obrigatória.")
    @FutureOrPresent(message = "A data de início deve ser atual ou futura.")
    private LocalDateTime dataInicio;

    @NotNull(message = "A data de término é obrigatória.")
    @Future(message = "A data de término deve estar no futuro.")
    private LocalDateTime dataFim;
}
