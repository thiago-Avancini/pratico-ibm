package com.teste.pratico.model;

import com.teste.pratico.enums.TipoMovimentacao;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExtratoRequest {

    @NotBlank(message = "Número da conta deve ser informado")
    private String numeroConta;
}
