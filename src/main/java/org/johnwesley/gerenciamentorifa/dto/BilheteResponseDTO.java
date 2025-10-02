package org.johnwesley.gerenciamentorifa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BilheteResponseDTO {
    private Long id;
    private String numero;
    private Boolean statusPagamento;
    private Long usuarioId;
    private Long rifaId;
}
