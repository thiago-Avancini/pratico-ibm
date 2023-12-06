package com.teste.pratico.core;

import com.teste.pratico.entity.Conta;
import com.teste.pratico.model.MovimentoRequest;
import com.teste.pratico.repository.ContaRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class Deposito extends Movimentacao {

  @Override
  public void efetuaMovimentacao(MovimentoRequest request) {
    Conta conta = consultarConta(request.getContaProprietaria());
    conta.efetuarMovimento(request, conta);
    conta.setSaldoAtual(conta.getSaldoAtual() + request.getValor());
    atualizarConta(conta);
  }
}
