package app.mapper;

import app.dto.pedidoDto.PedidoRequestDTO;
import app.model.Pedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    Pedido toPedidos(PedidoRequestDTO dto);
}