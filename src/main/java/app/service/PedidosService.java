package app.service;

import app.exeception.AraujoExeception;
import app.model.ItemPedido;
import app.model.Pedidos;
import app.repository.PedidosRepository;
import app.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidosService {

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoRepository produtoRepository;

    private List<ItemPedido> itensCompras = new ArrayList<ItemPedido>();
    private Pedidos compra = new Pedidos();


 /*   public Pedidos cadastrarPedidos(PedidosRequestDTO pedidos, String email, String senha) {

        Pedidos novoPedido = new Pedidos();
        Cliente cliente = clienteService.validarLogin(email, senha);
        List<ItemPedido> itemPedidoList = new ArrayList<>();
        ItemPedido itemPedido = null;

      *//*  for (ItensPedidosRequestDTO pedido : pedidos.getPedidos()) {
            Optional<Produto> produtoOptional = produtoRepository.findById(pedido.getId_produto());
            Produto produto = produtoOptional.get();
            if (pedido.getQuantidade() <= produto.quantidadeEstoque()) {
                itemPedido = new ItemPedido();
                itemPedido.setProduto(produto);
                itemPedido.setValorUnitario(converterValor((double) produto.getValorUnitario()));
                itemPedido.setQuantidade(pedido.getQuantidade());
                Double valorTotalItens = (double)(produto.getValorUnitario() * pedido.getQuantidade());
                itemPedido.setValorTotal(converterValor(valorTotalItens));
                validarProduto(produto.getCodProduto());
                produto.setQuantidade(produto.getQuantidade() - pedido.getQuantidade());
                itemPedidoList.add(itemPedido);
                itemPedido.setPedidos(novoPedido);
                if (produto.getQuantidade() == 0) {
                    produto.setDisponibilidade(DisponibilidadeProduto.INDISPONIVEL);
                }
            } else {
                throw new AraujoExeception("Quantidade de produto indisponivel", HttpStatus.UNAUTHORIZED);
            }*//*
        }

        novoPedido.setItensPedidos(itemPedidoList);
        novoPedido.setDataTransacao(LocalDate.now());
        novoPedido.setStatus(StatusPedido.PROCESSANDO);
       // novoPedido.setTransacao(cliente.getTransacao());
        Double valorTotal = calcularPedidos(pedidos, novoPedido);
        novoPedido.setValorTotal(converterValor(valorTotal));
        return pedidosRepository.save(novoPedido);
    }
*/
  /*  private static Double calcularPedidos(PedidosRequestDTO dto, Pedidos pedidos) {
        Double valorTotalPedidos = 0.0;
        for (int i = 0; i < dto.getPedidos().size(); i++) {
            valorTotalPedidos += pedidos.getItensPedidos().get(i).getValorTotal();
        }
        return valorTotalPedidos;
    }*/

    private Double converterValor(Double valor) {
        DecimalFormat fmt = new DecimalFormat("0.00");
        String string = fmt.format(valor);
        String[] part = string.split("[,]");
        String valorConvertido = part[0]+"."+part[1];
        return Double.parseDouble(valorConvertido);
    }

    public Double calcularTotalPedidos() {
        Double valorTotal = 0.0;
        for (ItemPedido item : itensCompras) {
          valorTotal += item.getValorTotal() + compra.getValorTotal();
        }
        return converterValor(valorTotal);
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

