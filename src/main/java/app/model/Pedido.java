package app.model;

import app.model.enums.DisponibilidadeProduto;
import app.model.enums.StatusPedido;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TB_PEDIDO")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Transacao transacao;

    @OneToMany(mappedBy = "pedidos", cascade = CascadeType.ALL)
    private List<ItemPedido> itemPedido;

    private LocalDate dataCompra;

    private String formaPagmento;

    private Double valorTotal = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "StatusPedido")
    private StatusPedido statusPedido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transacao getTransacao() {
        return transacao;
    }

    public void setTransacao(Transacao transacao) {
        this.transacao = transacao;
    }

    public List<ItemPedido> getItemPedido() {
        return itemPedido;
    }

    public void setItemPedido(List<ItemPedido> itemPedido) {
        this.itemPedido = itemPedido;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getFormaPagmento() {
        return formaPagmento;
    }

    public void setFormaPagmento(String formaPagmento) {
        this.formaPagmento = formaPagmento;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }
}