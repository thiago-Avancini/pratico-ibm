package com.teste.pratico.repository;

import com.teste.pratico.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

  Optional<Usuario> findByLogin(String login);
}
