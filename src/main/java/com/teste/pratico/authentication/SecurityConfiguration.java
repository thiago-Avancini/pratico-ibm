package com.teste.pratico.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.teste.pratico.enums.Permissao.GERENTE_DELETE;
import static com.teste.pratico.enums.Permissao.GERENTE_READ;
import static com.teste.pratico.enums.Permissao.GERENTE_UPDATE;
import static com.teste.pratico.enums.Permissao.GERENTE_WRITE;
import static com.teste.pratico.enums.Role.CLIENTE;
import static com.teste.pratico.enums.Role.GERENTE;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutService logoutService;

  private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**"};

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(req ->
            req.requestMatchers(WHITE_LIST_URL)
                .permitAll()
                .requestMatchers("/api/v1/cliente/**").hasRole(CLIENTE.name())
                .requestMatchers("/api/v1/gerencia/**").hasRole(GERENTE.name())
                .requestMatchers(GET,"/api/v1/gerencia/**").hasAuthority(GERENTE_READ.name())
                .requestMatchers(POST, "api/v1/gerencia/**").hasAuthority(GERENTE_WRITE.name())
                .requestMatchers(PUT, "api/v1/gerencia/**").hasAuthority(GERENTE_UPDATE.name())
                .requestMatchers(DELETE, "api/v1/gerencia/**").hasAuthority(GERENTE_DELETE.name())
                .requestMatchers(GET,"/api/v1/cliente/**").hasRole(CLIENTE.name())
                .requestMatchers(POST, "api/v1/cliente/**").hasRole(CLIENTE.name())
                .requestMatchers(PUT, "api/v1/cliente/**").hasRole(CLIENTE.name())
                .anyRequest()
                .authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .logout(logout ->
            logout.logoutUrl("/api/v1/auth/logout")
                 .addLogoutHandler(logoutService)
                .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext())));

    return http.build();
  }

}
