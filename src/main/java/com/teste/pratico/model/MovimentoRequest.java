package com.teste.pratico.model;

import com.teste.pratico.enums.TipoMovimentacao;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MovimentoRequest {

    private String contaMovimento;
    private String contaProprietaria;

    @NotBlank(message = "O tipo de movimentação é obrigatório")
    private TipoMovimentacao tipoMovimentacao;

    @NotBlank(message = "O valor da movimentação é obrigatório")
    private Double valor;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;





}
