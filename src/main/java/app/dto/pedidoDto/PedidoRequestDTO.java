package app.dto.pedidoDto;

import app.dto.itemPedidoDto.ItemPedidoRequestDTO;

import java.util.List;

public class PedidoRequestDTO {

    private List<ItemPedidoRequestDTO> pedidos;

    public List<ItemPedidoRequestDTO> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<ItemPedidoRequestDTO> pedidos) {
        this.pedidos = pedidos;
    }
}