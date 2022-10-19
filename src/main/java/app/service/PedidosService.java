package app.service;

import app.exeception.AraujoExeception;
import app.model.ItemPedido;
import app.model.Pedidos;
import app.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidosService {

    @Autowired
    private ProdutoRepository produtoRepository;
    private List<ItemPedido> itensCompras = new ArrayList<ItemPedido>();
    private Pedidos compra = new Pedidos();

    public Double calcularTotalPedidos() {
        Double valorTotal = 0.0;
        for (ItemPedido item : itensCompras) {
            valorTotal += item.getValorTotal() + compra.getValorTotal();
        }
        return valorTotal;
    }

    public void validarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new AraujoExeception("Produto não encontrado!", HttpStatus.NO_CONTENT);
        }
    }

    public List<ItemPedido> getItensCompras() {
        return itensCompras;
    }

    public void setItensCompras(List<ItemPedido> itensCompras) {
        this.itensCompras = itensCompras;
    }

    public Pedidos getCompra() {
        return compra;
    }

    public void setCompra(Pedidos compra) {
        this.compra = compra;
    }
}

