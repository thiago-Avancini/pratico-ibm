package com.teste.pratico.service;

import com.teste.pratico.authentication.JwtService;
import com.teste.pratico.enums.Role;
import com.teste.pratico.entity.Usuario;
import com.teste.pratico.interfaces.AdministraUsuario;
import com.teste.pratico.model.UsuarioRequest;
import com.teste.pratico.model.UsuarioResponse;
import com.teste.pratico.repository.UsuarioRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService implements AdministraUsuario {

  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  @Override
  public UsuarioResponse registrarNovoUsuario(UsuarioRequest request) {

    // Senha de 4 digitos
    String senha = String.valueOf(Instant.now().getNano()).substring(0, 4);

    Usuario usuario = Usuario.builder()
        .nome(request.getNome())
        .sobrenome(request.getSobrenome())
        .login(request.getCpf())
        .role(Role.valueOf(request.getTipoUsuario()))
        .senha(passwordEncoder.encode(senha))
        .build();

    Usuario novoUsuario = usuarioRepository.save(usuario);

    return UsuarioResponse.builder()
        .nomeCompleto(String.join(" ", novoUsuario.getNome(), novoUsuario.getSobrenome()))
        .cpf(novoUsuario.getLogin())
        .senhaTemporaria(senha)
        .build();

  }

}
