package br.com.araujo.loja.models;


import javax.persistence.*;
import java.nio.channels.Pipe;
import java.util.Date;

@Entity
@Table(name = "compra")
public class Compra {
    public Compra(){
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCompra=new Date();

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

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
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
