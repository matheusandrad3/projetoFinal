package app.dto.relatorioDto;

import java.time.LocalDate;

public class RelatorioItemPedidoResponseDTO {

    public RelatorioItemPedidoResponseDTO() {
    }

    public RelatorioItemPedidoResponseDTO(String nome, LocalDate dataCompra, Double valorUnitario, Integer quantidade, Double valorTotal) {
        this.nome = nome;
        this.dataCompra = dataCompra.toString();
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
    }

    private String nome;

    private String dataCompra;

    private Double valorUnitario;

    private Integer quantidade;

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

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
