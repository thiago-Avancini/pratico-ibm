package com.teste.pratico.repository;

import com.teste.pratico.entity.Extrato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtratoRepository extends JpaRepository<Extrato, String> {

}
