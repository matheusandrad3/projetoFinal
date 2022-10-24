package app.repository;

import app.model.Cliente;
import app.model.Pedido;
import app.model.Transacao;
import app.model.enums.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    boolean existsByTransacao(Transacao transacao);

    @Query("select p from Pedido p where p.transacao = :transacao and  p.statusPedido = 'PROCESSANDO'")
    Pedido getByTransacao(Transacao transacao);

    @Query("from Pedido p where p.statusPedido = :statusPedido and p.transacao.cliente = :cliente")
    Optional<Pedido> findByStatusPedido(StatusPedido statusPedido, Cliente cliente);
}