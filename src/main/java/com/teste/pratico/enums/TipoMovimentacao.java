package com.teste.pratico.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TipoMovimentacao {

    DEPOSITO("Depósito"),
    SAQUE("Saque"),
    TRANSFERENCIA_RECEBIDA("Transferência recebida"),
    TRANSFERENCIA_ENVIADA("Transferência enviada"),
    SALDO("Saldo"),
    EXTRATO("Extrato");

    @Getter
    private final String tipoMovimentacao;
}
