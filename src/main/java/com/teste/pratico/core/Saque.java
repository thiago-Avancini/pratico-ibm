package com.teste.pratico.core;

import com.teste.pratico.entity.Conta;
import com.teste.pratico.model.MovimentoRequest;
import org.springframework.stereotype.Service;

@Service
public class Saque extends Movimentacao {

    @Override
    public void efetuaMovimentacao(MovimentoRequest request) {
        Conta conta = consultarConta(request.getContaProprietaria());

        verificarProprietarioConta(conta);

        conta.efetuarMovimento(request, conta);
        if (conta.getSaldoAtual() < request.getValor()) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        conta.setSaldoAtual(conta.getSaldoAtual() - request.getValor());
        atualizarConta(conta);
    }
}


