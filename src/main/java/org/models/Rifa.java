package org.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
    private Boolean statusSorteio = Boolean.FALSE;

    @OneToMany(mappedBy = "rifa")
    private List<Bilhete> bilhetes;


    public Bilhete realizarSorteio(List<Bilhete> bilhetesElegiveis){
        if(Boolean.TRUE.equals(statusSorteio)){
            throw new IllegalStateException("Sorteio já realizado para esta rifa.");
        }
        if(bilhetesElegiveis == null || bilhetesElegiveis.isEmpty()){
            throw new IllegalStateException("Nenhum bilhete pago disponível para sorteio.");
        }
        Bilhete vencedor = bilhetesElegiveis.get(ThreadLocalRandom.current().nextInt(bilhetesElegiveis.size()));
        this.statusSorteio = Boolean.TRUE;
        if (this.dataFim == null || this.dataFim.isAfter(LocalDateTime.now())) {
            this.dataFim = LocalDateTime.now();
        }
        return vencedor;
    }



}
