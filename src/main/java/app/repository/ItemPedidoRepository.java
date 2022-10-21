package app.repository;

import app.dto.relatorioDto.RelatorioItensPedidosResponseDTO;
import app.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    @Query("select new app.dto.relatorioDto.RelatorioItensPedidosResponseDTO" +
            "(ip.produto.nome, ip.pedidos.dataCompra, ip.valorUnitario, ip.quantidade, ip.valorTotal) " +
            "from ItemPedido ip where ip.pedidos.dataCompra BETWEEN :start AND :end")
    List<RelatorioItensPedidosResponseDTO> recuperarItensPedido(LocalDate start, LocalDate end);

}
