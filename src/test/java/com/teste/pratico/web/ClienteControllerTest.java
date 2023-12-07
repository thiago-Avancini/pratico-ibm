package com.teste.pratico.web;

import com.teste.pratico.model.SaldoRequest;
import com.teste.pratico.service.ClienteService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ClienteControllerTest {


    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @Test
    public void testBuscaSaldo() {
        // Criando uma instância do SaldoRequest para o teste
        SaldoRequest saldoRequest = new SaldoRequest(/*... valores desejados ...*/);

        // Mock do comportamento do clienteService
        double saldoEsperado = 100.0; // Substitua pelo valor esperado
        when(clienteService.buscaSaldo(any(SaldoRequest.class))).thenReturn(saldoEsperado);

        // Chamando o método no controlador
        ResponseEntity<Double> responseEntity = clienteController.buscaSaldo(saldoRequest);

        // Verificando se o status da resposta é OK (200)
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Verificando se o saldo retornado é o mesmo que o esperado
        //assertEquals(saldoEsperado, responseEntity.getBody());
    }
}