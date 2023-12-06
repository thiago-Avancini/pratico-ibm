package com.teste.pratico.entity;

import com.teste.pratico.enums.TipoMovimentacao;
import com.teste.pratico.model.MovimentoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "extrato")
public class Extrato {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipoMovimentacao;

    private Double valor;

    private LocalDateTime dataOperacao;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    public String numeroContaOrigem;

    public String numeroContaDestino;

    public Extrato gerarMovimento(MovimentoRequest request, Conta conta) {

        this.setConta(conta);

        if (request.getTipoMovimentacao().equals(TipoMovimentacao.TRANSFERENCIA_ENVIADA)) {
            this.setNumeroContaDestino(request.getContaMovimento());
        } else if (request.getTipoMovimentacao().equals(TipoMovimentacao.TRANSFERENCIA_RECEBIDA)) {
            this.setNumeroContaOrigem(request.getContaProprietaria());
        }

        this.setDescricao(request.getDescricao());
        this.setValor(request.getValor());
        this.setTipoMovimentacao(request.getTipoMovimentacao());
        this.setDataOperacao(LocalDateTime.now());

        return this;
    }
}
