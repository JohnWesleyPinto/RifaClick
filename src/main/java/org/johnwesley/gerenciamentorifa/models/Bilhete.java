package org.johnwesley.gerenciamentorifa.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bilhete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private Boolean statusPagamento;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "rifaId")
    private Rifa rifa;


}
