package com.teste.pratico.core;

import com.teste.pratico.entity.Conta;
import com.teste.pratico.enums.TipoMovimentacao;
import com.teste.pratico.model.MovimentoRequest;
import org.springframework.stereotype.Service;

@Service
public class Transferencia extends Movimentacao {

    @Override
    public void efetuaMovimentacao(MovimentoRequest request) {

        Conta contaProprietaria = consultarConta(request.getContaProprietaria());

        verificarProprietarioConta(contaProprietaria);

        Conta contaMovimento = consultarConta(request.getContaMovimento());

        if(TipoMovimentacao.TRANSFERENCIA_ENVIADA.equals(request.getTipoMovimentacao())) {
            contaProprietaria.efetuarMovimento(request, contaProprietaria);
            contaProprietaria.setSaldoAtual(contaProprietaria.getSaldoAtual() - request.getValor());
            request.setTipoMovimentacao(TipoMovimentacao.TRANSFERENCIA_RECEBIDA);
            contaMovimento.efetuarMovimento(request, contaMovimento);
            contaMovimento.setSaldoAtual(contaMovimento.getSaldoAtual() + request.getValor());
        } else if (TipoMovimentacao.TRANSFERENCIA_RECEBIDA.equals(request.getTipoMovimentacao())) {
            contaProprietaria.efetuarMovimento(request, contaProprietaria);
            contaProprietaria.setSaldoAtual(contaProprietaria.getSaldoAtual() - request.getValor());
            request.setTipoMovimentacao(TipoMovimentacao.TRANSFERENCIA_ENVIADA);
            contaMovimento.efetuarMovimento(request, contaMovimento);
            contaMovimento.setSaldoAtual(contaMovimento.getSaldoAtual() - request.getValor());
        }
        atualizarConta(contaMovimento);
        atualizarConta(contaProprietaria);
    }
}

