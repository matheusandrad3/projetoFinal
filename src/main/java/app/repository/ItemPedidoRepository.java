package app.repository;

import app.dto.relatorioDto.RelatorioItemPedidoResponseDTO;
import app.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    @Query("select new app.dto.relatorioDto.RelatorioItemPedidoResponseDTO" +
            "(ip.produto.nome, ip.pedidos.dataCompra, ip.valorUnitario, ip.quantidade, ip.valorTotal) " +
            "from ItemPedido ip where ip.pedidos.dataCompra BETWEEN :start AND :end " +
            "AND ip.pedidos.statusPedido = 'CONCLUIDO' order by ip.pedidos.id desc")
    List<RelatorioItemPedidoResponseDTO> recuperarItensPedido(LocalDate start, LocalDate end);

    @Query("from ItemPedido ip where ip.pedidos.id = :id and ip.produto.disponibilidade = 'DISPONIVEL'")
    List<ItemPedido> findAllByIdPedido(Long id);
}