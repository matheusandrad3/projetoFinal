package app.repository;

import app.model.Pedidos;
import app.model.RelatorioItensPedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RelatorioItensPedidoRepository extends JpaRepository<RelatorioItensPedidos, String> {

    @Query(value = "Select * from relatorio where data_compra >= :dataInicio AND data_compra <= :dataFinal", nativeQuery = true)
    List<RelatorioItensPedidos> findAllPedidos(String dataInicio, String dataFinal);
}
