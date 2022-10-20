package app.repository;

import app.model.Pedidos;
import app.model.Teste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import java.util.List;

public interface PedidosRepository extends JpaRepository<Pedidos, Long> {

    /*
    @Query(value = "Select * from tb_pedidos where data_compra >= :dataInicio AND data_compra <= :dataFinal", nativeQuery = true)
    List<Pedidos> findAllPedidos(String dataInicio, String dataFinal);


    // Query nativa

    @Query(value = "SELECT p.nome, pd.data_compra, i.valor_unitario, i.quantidade, i.valor_total " +
            "FROM tb_itens_pedido as i " +
            "INNER JOIN  tb_pedidos AS pd ON i.id_pedidos = pd.transacao_id " +
            "INNER JOIN produto AS p ON i.id_produtos = p.id " +
            "where data_compra >=  :dataInicio AND data_compra <= :dataFinal", nativeQuery = true)

     */

    // Query JQPA

    @Query("Select new app.model.Teste(p.nome, pd.dataCompra, i.valorUnitario, i.quantidade, i.valorTotal) " +
            "FROM ItemPedido i " +
            "INNER JOIN Pedidos pd " +
            "INNER JOIN Produto p " +
            "WHERE dataCompra >= :dataInicio and dataCompra <= :dataFinal")

    List<Teste> findAllPedidos(@Param("dataInicio") String dataInicio, @Param("dataFinal") String dataFinal);



}
