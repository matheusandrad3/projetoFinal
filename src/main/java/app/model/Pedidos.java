package app.model;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "TB_PEDIDOS")
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    private LocalDate dataCompra;

    private String formaPagmento;
    private Double valorTotal=0.;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
}
