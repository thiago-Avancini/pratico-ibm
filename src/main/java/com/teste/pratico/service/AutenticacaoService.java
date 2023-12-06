package com.teste.pratico.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.pratico.authentication.JwtService;
import com.teste.pratico.authentication.LoginRequest;
import com.teste.pratico.authentication.LoginResponse;
import com.teste.pratico.entity.Token;
import com.teste.pratico.enums.TokenType;
import com.teste.pratico.entity.Usuario;
import com.teste.pratico.repository.TokenRepository;
import com.teste.pratico.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoService {

  private final UsuarioRepository usuarioRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public LoginResponse login(LoginRequest request) {

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getSenha()));

    Usuario usuario = usuarioRepository.findByLogin(request.getLogin()).orElseThrow();

    String jwtToken = jwtService.gerarToken(usuario);
    String refreshToken = jwtService.gerarRefreshToken(usuario);

    revogarTokensUsuario(usuario);
    salvarTokenUsuario(usuario, jwtToken);

    return LoginResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  private void revogarTokensUsuario(Usuario usuario) {

    List<Token> tokensValidos = tokenRepository.findAllValidTokenByUser(usuario.getId());

    if (!tokensValidos.isEmpty()) {
      tokensValidos.forEach(token -> {
        token.setRevogado(true);
        token.setExpirado(true);
      });
      tokenRepository.saveAll(tokensValidos);
    }
  }

  private void salvarTokenUsuario(Usuario usuario, String token) {

    Token tokenNovo = Token.builder().usuario(usuario).token(token).tokenType(TokenType.BEARER).expirado(false)
        .revogado(false).build();

    tokenRepository.save(tokenNovo);
  }

  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String login;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    login = jwtService.extrairLogin(refreshToken);
    if (login != null) {
      var user = usuarioRepository.findByLogin(login)
          .orElseThrow();
      if (jwtService.isValidToken(refreshToken, user)) {
        var accessToken = jwtService.gerarToken(user);
        revogarTokensUsuario(user);
        salvarTokenUsuario(user, accessToken);
        var authResponse = LoginResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
