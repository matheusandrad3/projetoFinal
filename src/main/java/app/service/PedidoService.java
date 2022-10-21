package app.service;

import app.exeception.AraujoExeception;
import app.model.Cliente;
import app.model.ItemPedido;
import app.model.Pedido;
import app.model.Produto;
import app.repository.PedidoRepository;
import app.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    private List<ItemPedido> itensCompras = new ArrayList<ItemPedido>();
    private Pedido compra = new Pedido();

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

    public void removerCarrinho(Long id) {
        List<ItemPedido> itensCompras = getItensCompras();
        for (ItemPedido itens : itensCompras) {
            if (itens.getProduto().getId().equals(id)) {
                itensCompras.remove(itens);
                break;
            }
        }
    }

    public void alterarQuantidade(Long id, Integer acao) {
        List<ItemPedido> itensCompras = getItensCompras();

        double valorTotal = 0.0;
        for (ItemPedido itens : itensCompras) {
            Integer quantidadeEstoque = produtoRepository.getQuantidadeEstoqueById(id);
            if (itens.getProduto().getId().equals(id)) {
                if (acao.equals(1) && itens.getQuantidade() < quantidadeEstoque) {
                    itens.setQuantidade(itens.getQuantidade() + 1);
                    itens.setValorTotal(0.);
                    valorTotal = itens.getValorTotal() + (itens.getQuantidade() * itens.getValorUnitario());
                    itens.setValorTotal(converterValor(valorTotal));

                } else if (acao == 0) {
                    itens.setQuantidade(itens.getQuantidade() - 1);
                    if (itens.getQuantidade() == 0) {
                        removerCarrinho(id);
                    }
                    itens.setValorTotal(0.0);
                    valorTotal = itens.getValorTotal() + (itens.getQuantidade() * itens.getValorUnitario());
                    itens.setValorTotal(converterValor(valorTotal));
                }
                break;
            }
        }
    }

    public void addCarrinho(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        Produto produto1 = produto.get();
        List<ItemPedido> itensCompras = getItensCompras();
        int controle = 0;
        for (ItemPedido itens : itensCompras) {
            if (itens.getProduto().getId().equals(produto1.getId())) {
                itens.setQuantidade(itens.getQuantidade() + 1);
                itens.setValorTotal(0.0);
                double valorTotal = itens.getValorTotal() + (itens.getQuantidade() * itens.getValorUnitario());
                itens.setValorTotal(converterValor(valorTotal));
                controle = 1;
                break;
            }
        }
        if (controle == 0) {
            ItemPedido item = new ItemPedido();
            item.setProduto(produto1);
            item.setValorUnitario((double) produto1.getValorVenda());
            item.setQuantidade(item.getQuantidade() + 1);
            double valorTotal = item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario());
            item.setValorTotal(converterValor(valorTotal));
            itensCompras.add(item);
        }
    }

    public boolean finalizarCompra(Cliente cliente, Pedido compra, List<ItemPedido> itensCompras, String formaPagmento) {
        compra.setTransacao(cliente.getTransacao());
        compra.setFormaPagmento(formaPagmento);
        compra.setDataCompra(LocalDate.now());
        Double valorTotal = calcularTotalPedidos();
        if(valorTotal != 0){
            compra.setValorTotal(valorTotal);
            for (ItemPedido i : itensCompras) {
                i.setPedidos(compra);
                consumirEstoque(i.getProduto().getId(), i.getQuantidade());
            }
            compra.setItemPedido(itensCompras);

            pedidoRepository.save(compra);

            setItensCompras(new ArrayList<>());
            setCompra(new Pedido());
            return true;
        }
        return false;
    }

    private Double converterValor(Double valor) {
        DecimalFormat fmt = new DecimalFormat("0.00");
        String string = fmt.format(valor);
        String[] part = string.split("[,]");
        String valorConvertido = part[0] + "." + part[1];
        return Double.parseDouble(valorConvertido);
    }


    public List<ItemPedido> getItensCompras() {
        return itensCompras;
    }

    public void setItensCompras(List<ItemPedido> itensCompras) {
        this.itensCompras = itensCompras;
    }

    public Pedido getCompra() {
        return compra;
    }

    public void setCompra(Pedido compra) {
        this.compra = compra;
    }
}

