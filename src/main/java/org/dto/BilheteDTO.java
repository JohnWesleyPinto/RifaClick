package org.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BilheteDTO {
    private Long rifaId;
    private Long usuarioId;
    private Boolean statusPagamento;
}
