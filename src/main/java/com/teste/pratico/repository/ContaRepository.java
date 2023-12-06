package com.teste.pratico.repository;

import com.teste.pratico.entity.Conta;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContaRepository extends JpaRepository<Conta, String> {

    Optional<Conta> findByCpf(String cpf);

    Optional<Conta> findByNumeroConta(String numeroConta);

    @Query("SELECT conta FROM Conta conta JOIN FETCH conta.extratos extrato where conta.numeroConta = :numeroConta")
    Conta findConta(@Param("numeroConta") String numeroConta);

}
