package com.teste.pratico.entity;

import com.teste.pratico.enums.TipoMovimentacao;
import com.teste.pratico.model.ExtratoRequest;
import com.teste.pratico.model.MovimentoRequest;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conta")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conta", fetch = FetchType.LAZY)
    private Set<Extrato> extratos = new HashSet<>();

    private String nomeCliente;
    private String cpf;

    private String numeroConta;
    private Double saldoAtual;

    public static Conta criarNovaConta(String nomeCliente, String cpf) {
        return Conta.builder()
            .nomeCliente(nomeCliente)
            .cpf(cpf)
            .numeroConta(gerarNumeroConta())
            .saldoAtual(gerarSaldoInicial())
            .build();
    }
    private static String gerarNumeroConta() {
        Random random = new Random();
        Integer contaGerada = random.nextInt(100000);
        return String.format("%06d", contaGerada);
    }

    private static Double gerarSaldoInicial() {
        return 0d;
    }

    public void efetuarMovimento(MovimentoRequest request, Conta conta) {
       Extrato extrato = new Extrato();
       this.getExtratos().add(extrato.gerarMovimento(request, conta));
    }
}
