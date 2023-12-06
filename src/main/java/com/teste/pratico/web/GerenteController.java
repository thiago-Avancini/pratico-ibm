package com.teste.pratico.web;

import com.teste.pratico.model.ContaRequest;
import com.teste.pratico.model.ContaResponse;
import com.teste.pratico.service.GerenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gerente")
@RequiredArgsConstructor
public class GerenteController {

    private final GerenteService gerenteService;

    @PostMapping("/nova-conta")
    @PreAuthorize("hasRole('ROLE_GERENTE')")
    public ResponseEntity<ContaResponse> registrarNovaConta(@RequestBody ContaRequest request) {
        return ResponseEntity.ok(gerenteService.registrarNovaConta(request));
    }

}
