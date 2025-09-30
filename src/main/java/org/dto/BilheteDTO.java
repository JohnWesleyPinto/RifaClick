package org.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BilheteDTO {
    private Long id;
    private String numero;
    private Long rifaId;
    private Long usuarioId;
    private Boolean statusPagamento;
}
