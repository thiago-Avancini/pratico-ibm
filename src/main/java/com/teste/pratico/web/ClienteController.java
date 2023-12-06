package com.teste.pratico.web;

import com.teste.pratico.enums.TipoMovimentacao;
import com.teste.pratico.model.ExtratoResponse;
import com.teste.pratico.model.MovimentoRequest;
import com.teste.pratico.model.MovimentoResponse;
import com.teste.pratico.model.SaldoRequest;
import com.teste.pratico.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/saldo")
    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    public ResponseEntity<Double> buscaSaldo(@RequestBody SaldoRequest request) {
        return ResponseEntity.ok(clienteService.buscaSaldo(request));
    }

    @PostMapping("/extrato")
    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    public ResponseEntity<List<ExtratoResponse>> buscaExtrato(@RequestBody SaldoRequest request) {
        return ResponseEntity.ok(clienteService.buscaExtrato(request));
    }

    @PostMapping("/movimentacao")
    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    public ResponseEntity<MovimentoResponse> movimentaConta(@RequestBody MovimentoRequest request) {
        return ResponseEntity.ok(clienteService.movimentaConta(request));
    }

}
