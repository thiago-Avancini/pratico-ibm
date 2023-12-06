package com.teste.pratico.repository;

import com.teste.pratico.entity.Token;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query(value = "select t from Token t inner join Usuario u on t.usuario.id = u.id where u.id = :id and (t.expirado = false or t.revogado = false)")
  List<Token> findAllValidTokenByUser(@Param("id") String id);

  Optional<Token> findByToken(String token);
}
