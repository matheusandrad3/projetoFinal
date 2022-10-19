package app.service;

import app.exeception.AraujoExeception;
import app.model.Cliente;
import app.model.ItemPedido;
import app.model.Pedidos;
import app.model.Produto;
import app.model.enums.DisponibilidadeProduto;
import app.repository.PedidosRepository;
import app.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private PedidosService pedidosService;

    public Produto cadastrarProduto(Produto produto) {
        produto.setDisponibilidade(DisponibilidadeProduto.DISPONIVEL);
        return produtoRepository.save(produto);
    }

    public void consumirEstoque(Long id, Integer quantidade) {
        Optional<Produto> produto = produtoRepository.findById(id);
        Produto p = produto.get();
        if (p.getQuantidadeEstoque() >= quantidade) {
            p.setQuantidadeEstoque(p.getQuantidadeEstoque() - quantidade);
            produtoRepository.save(p);
        } else {
            throw new AraujoExeception("Quantidade indisponível!", HttpStatus.NOT_FOUND);
        }
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }


    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public Optional<Produto> atualizarProduto(Long id) {
        return produtoRepository.findById(id);
    }

    public void removerCarrinho(Long id){
        List<ItemPedido> itensCompras = pedidosService.getItensCompras();
        for (ItemPedido itens : itensCompras) {
            if (itens.getProduto().getId().equals(id)) {
                itensCompras.remove(itens);
                break;
            }
        }
    }

    public void alterarQuantidade(Long id, Integer acao){
        List<ItemPedido> itensCompras = pedidosService.getItensCompras();
        for (ItemPedido itens : itensCompras) {
            if (itens.getProduto().getId().equals(id)) {
                if (acao.equals(1)) {
                    itens.setQuantidade(itens.getQuantidade() + 1);
                    itens.setValorTotal(0.);
                    itens.setValorTotal(itens.getValorTotal() + (itens.getQuantidade() * itens.getValorUnitario()));

                } else if (acao == 0) {
                    itens.setQuantidade(itens.getQuantidade() - 1);
                    if (itens.getQuantidade() == 0){
                        removerCarrinho(id);
                    }
                    itens.setValorTotal(0.);
                    itens.setValorTotal(itens.getValorTotal() + (itens.getQuantidade() * itens.getValorUnitario()));
                }
                break;
            }
        }
    }

    public void addCarrinho(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        Produto produto1 = produto.get();
        List<ItemPedido> itensCompras = pedidosService.getItensCompras();
        int controle = 0;
        for (ItemPedido itens : itensCompras) {
            if (itens.getProduto().getId().equals(produto1.getId())) {
                itens.setQuantidade(itens.getQuantidade() + 1);
                itens.setValorTotal(0.);
                itens.setValorTotal(itens.getValorTotal() + (itens.getQuantidade() * itens.getValorUnitario()));
                controle = 1;
                break;
            }
        }
        if (controle == 0) {
            ItemPedido item = new ItemPedido();
            item.setProduto(produto1);
            item.setValorUnitario((double) produto1.getValorVenda());
            item.setQuantidade(item.getQuantidade() + 1);
            item.setValorTotal(item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario()));
            itensCompras.add(item);
        }
    }

    public void finalizarCompra(Cliente cliente, Pedidos compra, List<ItemPedido> itensCompras,String formaPagmento) {
        compra.setTransacao(cliente.getTransacao());
        compra.setFormaPagmento(formaPagmento);
        compra.setDataCompra(LocalDate.now());
        compra.setValorTotal(pedidosService.calcularTotalPedidos());
        for (ItemPedido i : itensCompras) {
            i.setPedidos(compra);
            consumirEstoque(i.getProduto().getId(), i.getQuantidade());
        }
        compra.setItemPedido(itensCompras);

        pedidosRepository.save(compra);

        pedidosService.setItensCompras(new ArrayList<>());
        pedidosService.setCompra(new Pedidos());
    }
}
