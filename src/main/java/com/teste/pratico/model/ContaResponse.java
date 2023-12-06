package com.teste.pratico.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContaResponse {

    private String nomeCompleto;
    private String numeroConta;
    private String login;
    private String senhaTemporaria;

}
