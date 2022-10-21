package app.dto.relatorioDto;

import java.time.LocalDate;

public class RelatorioItensPedidosResponseDTO {

    public RelatorioItensPedidosResponseDTO() {
    }

    public RelatorioItensPedidosResponseDTO(String nome, LocalDate dataCompra, Double valorUnitario, Integer quantidade, Double valorTotal) {
        this.nome = nome;
        this.dataCompra = dataCompra.toString();
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade.doubleValue();
        this.valorTotal = valorTotal;
    }

    private String nome;

    private String dataCompra;

    private Double valorUnitario;

    private Double quantidade;

    private Double valorTotal;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
