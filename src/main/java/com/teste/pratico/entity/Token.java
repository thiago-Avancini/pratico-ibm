package com.teste.pratico.entity;

import com.teste.pratico.enums.TokenType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  public String id;

  @Column(unique = true)
  private String token;

  @Enumerated(EnumType.STRING)
  private TokenType tokenType = TokenType.BEARER;

  private boolean revogado;

  private boolean expirado;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private Usuario usuario;

  public boolean isExpirado() {
    return expirado;
  }
}
