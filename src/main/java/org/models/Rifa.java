package org.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Rifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String premio;
    private Integer quantidadeBilhetes;
    private BigDecimal precoBilhete;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Boolean statusSorteio;

    @OneToMany(mappedBy = "Rifa")
    private List<Bilhete> bilhetes;


    private Bilhete realizarSorteio(Boolean sortear){
        getBilhetes()
    }



}
