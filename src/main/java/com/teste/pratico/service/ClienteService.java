package com.teste.pratico.service;

import com.teste.pratico.core.Movimentacao;
import com.teste.pratico.core.MovimentacaoFactory;
import com.teste.pratico.entity.Conta;
import com.teste.pratico.entity.Extrato;
import com.teste.pratico.enums.TipoMovimentacao;
import com.teste.pratico.model.ExtratoRequest;
import com.teste.pratico.model.ExtratoResponse;
import com.teste.pratico.model.MovimentoRequest;
import com.teste.pratico.model.MovimentoResponse;
import com.teste.pratico.model.SaldoRequest;
import com.teste.pratico.repository.ContaRepository;
import com.teste.pratico.repository.ExtratoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ContaRepository contaRepository;
    private final ModelMapper modelMapper;
    private final MovimentacaoFactory factory;

    public Double buscaSaldo(SaldoRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Conta conta = contaRepository.findByNumeroConta(request.getNumeroConta())
            .orElseThrow(() -> new IllegalArgumentException("Conta informada não existente"));

        if (!userDetails.getUsername().equals(conta.getCpf())) {
            throw new IllegalArgumentException("Esse usuário não tem acesso a esta conta");
        }

        return conta.getSaldoAtual();
    }

    public List<ExtratoResponse> buscaExtrato(SaldoRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Conta conta = contaRepository.findByNumeroConta(request.getNumeroConta())
            .orElseThrow(() -> new IllegalArgumentException("Conta informada não existente"));

        if (!userDetails.getUsername().equals(conta.getCpf())) {
            throw new IllegalArgumentException("Esse usuário não tem acesso a esta conta");
        }

        return conta.getExtratos().stream()
            .map(mov -> modelMapper.map(mov, ExtratoResponse.class)
            ).collect(Collectors.toList());

    }

    public MovimentoResponse movimentaConta(MovimentoRequest request) {
        Movimentacao movimentacao = factory.defineMovimentacao(request.getTipoMovimentacao());
        movimentacao.efetuaMovimentacao(request);
        return MovimentoResponse.builder()
                .mensagem("Movimentação realizada com sucesso")
                .build();
    }

}
