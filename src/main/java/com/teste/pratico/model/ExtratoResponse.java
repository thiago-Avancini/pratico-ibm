package com.teste.pratico.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExtratoResponse {
    private String descricao;
    private String tipoMovimentacao;
    private Double valor;
    private LocalDateTime dataOperacao;
    private String numeroContaOrigem;
    private String numeroContaDestino;
}
