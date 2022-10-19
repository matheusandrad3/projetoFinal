package app.repository;

import app.model.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidosRepository  extends JpaRepository<Pedidos, Long> {

    @Query(value="Select * from tb_pedidos where data_compra >= :dataInicio AND data_compra <= :dataFinal", nativeQuery = true)
    List<Pedidos> findAllPedidos(String dataInicio, String dataFinal);


}
