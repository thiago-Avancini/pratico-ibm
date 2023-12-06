package com.teste.pratico.configuration;

import com.teste.pratico.entity.Conta;
import com.teste.pratico.entity.Extrato;
import com.teste.pratico.model.ContaRequest;
import com.teste.pratico.model.ContaResponse;
import com.teste.pratico.model.ExtratoResponse;
import com.teste.pratico.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final UsuarioRepository usuarioRepository;

  @Bean
  public UserDetailsService userDetailsService() {
    return login -> usuarioRepository.findByLogin(login)
        .orElseThrow(() -> new UsernameNotFoundException("User or password invalid"));
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());

    return authenticationProvider;
  }

  @Bean
  public AuditorAware<String> auditorAware() {
    return new ApplicationAuditAware();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();

    modelMapper.createTypeMap(ContaRequest.class, Conta.class)
        .addMapping(src -> String.join(" ", src.getNome(), src.getSobrenome()), Conta::setNomeCliente);

    modelMapper.createTypeMap(Conta.class, ContaResponse.class)
        .addMapping(src -> src.getCpf(), ContaResponse::setLogin)
        .addMapping(src -> src.getNomeCliente(),ContaResponse::setNomeCompleto);

//    modelMapper.createTypeMap(Extrato.class, ExtratoResponse.class)
//        .addMapping(src -> src.get, ExtratoResponse::setXXXX);

    return modelMapper;
  }
}
