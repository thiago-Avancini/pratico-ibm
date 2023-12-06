package com.teste.pratico.service;

import com.teste.pratico.authentication.JwtService;
import com.teste.pratico.entity.Conta;
import com.teste.pratico.entity.Usuario;
import com.teste.pratico.enums.Role;
import com.teste.pratico.model.ContaRequest;
import com.teste.pratico.model.ContaResponse;
import com.teste.pratico.repository.ContaRepository;
import com.teste.pratico.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GerenteService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ContaRepository contaRepository;
    private final ModelMapper modelMapper;

    public ContaResponse registrarNovaConta(ContaRequest request) {
        String senha = String.valueOf(Instant.now().getNano()).substring(0, 4);

        Optional<Usuario> user = usuarioRepository.findByLogin(request.getCpf());
        user.ifPresent(e -> {
            throw new IllegalArgumentException("Usuário já cadastrado");
        });


        Optional<Conta> account = contaRepository.findByCpf(request.getCpf());
        account.ifPresent(e -> {
            throw new IllegalArgumentException("Conta já cadastrada");
        });

        Usuario usuario = Usuario.builder()
                .nome(request.getNome())
                .sobrenome(request.getSobrenome())
                .login(defineCpf(request.getCpf()))
                .role(Role.CLIENTE)
                .senha(passwordEncoder.encode(senha))
                .build();

        usuarioRepository.save(usuario);

        Conta conta = Conta.criarNovaConta(
            String.join(" ", request.getNome(), request.getSobrenome()),
            defineCpf(request.getCpf()));

        Conta novaConta = contaRepository.save(conta);

        ContaResponse response = modelMapper.map(novaConta, ContaResponse.class);
        response.setSenhaTemporaria(senha);

        return response;
    }

    private String defineCpf(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("CPF está incorreto.");
        }
        return cpf;
    }

}
