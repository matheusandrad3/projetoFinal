package app.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TB_TRANSACAO")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "transacao")
    private List<Pedido> pedidos;

    @OneToOne
    private Cliente cliente;

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

    public List<Pedido> getCarrinhos() {
        return pedidos;
    }

    public void setCarrinhos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}