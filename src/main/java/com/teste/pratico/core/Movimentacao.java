package com.teste.pratico.core;

import com.teste.pratico.entity.Conta;
import com.teste.pratico.model.MovimentoRequest;
import com.teste.pratico.repository.ContaRepository;
import com.teste.pratico.repository.ExtratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public abstract class Movimentacao {

  protected ContaRepository contaRepository;
  protected ExtratoRepository extratoRepository;

  protected void verificarProprietarioConta(Conta conta){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    if (!userDetails.getUsername().equals(conta.getCpf())) {
      throw new IllegalArgumentException("Esse usuário não tem acesso a esta conta");
    }
  }

  @Autowired
  public final void setContaRepository(ContaRepository contaRepository, ExtratoRepository extratoRepository) {
    this.contaRepository = contaRepository;
    this.extratoRepository = extratoRepository;
  }

  public abstract void efetuaMovimentacao(MovimentoRequest request);

  protected void atualizarConta(Conta conta) {
    contaRepository.save(conta);
  }

  protected Conta consultarConta(String numeroConta) {
    return contaRepository.findByNumeroConta(numeroConta)
            .orElseThrow(() -> new IllegalArgumentException("Conta não existente"));
  }
}
