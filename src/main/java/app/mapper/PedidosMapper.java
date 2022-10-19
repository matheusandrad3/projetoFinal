package app.mapper;

import app.dto.pedidosDto.PedidosRequestDTO;
import app.model.Pedidos;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PedidosMapper {

    Pedidos toPedidos(PedidosRequestDTO dto);
}
