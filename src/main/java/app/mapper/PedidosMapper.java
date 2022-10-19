package app.mapper;

import app.dto.pedidosDto.PedidosRequestDTO;
import app.model.ItemPedido;
import app.model.Pedidos;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidosMapper {


    Pedidos toPedidos(PedidosRequestDTO dto);
}
