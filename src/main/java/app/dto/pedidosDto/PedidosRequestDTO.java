package app.dto.pedidosDto;

import app.dto.itensPedidosDto.ItensPedidosRequestDTO;

import java.util.List;

public class PedidosRequestDTO {

    private List<ItensPedidosRequestDTO> pedidos;

    public List<ItensPedidosRequestDTO> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<ItensPedidosRequestDTO> pedidos) {
        this.pedidos = pedidos;
    }
}