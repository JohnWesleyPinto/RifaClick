package org.johnwesley.gerenciamentorifa.dto;

import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "O identificador da rifa é obrigatório")
    private Long rifaId;

    @NotNull(message = "O identificador do usuário é obrigatório")
    private Long usuarioId;

    @NotNull(message = "O status de pagamento é obrigatório")
    private Boolean statusPagamento;
}