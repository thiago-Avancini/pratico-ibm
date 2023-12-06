package com.teste.pratico.core;

import com.teste.pratico.enums.TipoMovimentacao;
import com.teste.pratico.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovimentacaoFactory {

  private final Deposito deposito;
  private final Saque saque;
  private final Transferencia transferencia;

  public Movimentacao defineMovimentacao(TipoMovimentacao tipo) {

    switch (tipo) {
      case SAQUE -> {
        return saque;
      }
      case TRANSFERENCIA_ENVIADA, TRANSFERENCIA_RECEBIDA -> {
        return transferencia;
      }
      case DEPOSITO -> {
        return deposito;
      }
      default -> {
        throw new IllegalArgumentException("Operação não permitida");
      }

    }
  }
}
