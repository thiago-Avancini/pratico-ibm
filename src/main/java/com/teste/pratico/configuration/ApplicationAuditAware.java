package com.teste.pratico.configuration;

import com.teste.pratico.entity.Usuario;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ApplicationAuditAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
      return Optional.empty();
    }

    Usuario usuario = (Usuario) authentication.getPrincipal();

    return Optional.ofNullable(usuario.getId());
  }
}
