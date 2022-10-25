package app.service;

import app.model.Cliente;
import app.model.ItemPedido;
import app.model.Pedido;
import app.model.Produto;
import app.model.enums.DisponibilidadeProduto;
import app.model.enums.StatusPedido;
import app.repository.ItemPedidoRepository;
import app.repository.PedidoRepository;
import app.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ClienteService clienteService;


    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Double calcularTotalPedidos(List<ItemPedido> itens, Pedido pedido) {
        Double valorTotal = 0.0;
        for (ItemPedido item : itens) {
            valorTotal += item.getValorTotal();
        }
        return converterValor(valorTotal);
    }

    public void removerCarrinho(Long id, Pedido pedido) {
        for (ItemPedido itens : listaPedido(pedido)) {
            if (itens.getProduto().getId().equals(id)) {
                itemPedidoRepository.delete(itens);
                Integer quantidade = itens.getQuantidade();
                itens.getProduto().setQuantidadeEstoque(itens.getProduto().getQuantidadeEstoque() + quantidade);
                if (itens.getProduto().getQuantidadeEstoque() > 0) {
                    itens.getProduto().setDisponibilidade(DisponibilidadeProduto.DISPONIVEL);
                }
                break;
            }
        }
        if (listaPedido(pedido).size() == 0) {
            pedido.setStatusPedido(StatusPedido.CANCELADO);
        }
        pedidoRepository.save(pedido);
    }

    public void alterarQuantidade(Long id, Integer acao) {
        Cliente cliente = clienteService.bucarUsuario();
        Pedido pedido = buscarPedido(cliente);
        List<ItemPedido> itensCompras = listaPedido(pedido);
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        Produto produto = produtoOptional.get();

        double valorTotal = 0.0;
        for (ItemPedido itens : itensCompras) {
            Integer quantidadeEstoque = produtoRepository.getQuantidadeEstoqueById(id);
            if (itens.getProduto().getId().equals(id)) {
                if (acao.equals(1) && itens.getQuantidade() < quantidadeEstoque) {
                    itens.setQuantidade(itens.getQuantidade() + 1);
                    itens.setValorTotal(0.);
                    valorTotal = itens.getValorTotal() + (itens.getQuantidade() * itens.getValorUnitario());
                    itens.setValorTotal(converterValor(valorTotal));
                    produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - 1);

                } else if (acao == 0) {
                    itens.setQuantidade(itens.getQuantidade() - 1);
                    produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + 1);
                    if (itens.getQuantidade() == 0) {
                        itemPedidoRepository.delete(itens);
                        itensCompras.remove(itens);
                        removerCarrinho(id, pedido);
                        break;
                    }
                    itens.setValorTotal(0.0);
                    valorTotal = itens.getValorTotal() + (itens.getQuantidade() * itens.getValorUnitario());
                    itens.setValorTotal(converterValor(valorTotal));
                }
                break;
            }

        }
        if (itensCompras.size() > 0) {
            pedido.setItemPedido(itensCompras);
        }
        pedido.setDataCompra(LocalDate.now());
        pedido.setValorTotal(calcularTotalPedidos(itensCompras, pedido));
        pedido.setTransacao(cliente.getTransacao());
        if (produto.getQuantidadeEstoque() > 0) {
            produto.setDisponibilidade(DisponibilidadeProduto.DISPONIVEL);
        }
        produtoRepository.save(produto);
        pedidoRepository.save(pedido);
    }

    public void addCarrinho(Long id) {
        Cliente cliente = clienteService.bucarUsuario();
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        Produto produto = produtoOptional.get();
        Pedido pedido = localizarPedido(cliente);
        List<ItemPedido> itensCompras = pedido.getItemPedido();

        int controle = 0;
        for (ItemPedido itens : itensCompras) {
            if (itens.getProduto().getId().equals(produto.getId())) {
                itens.setQuantidade(itens.getQuantidade() + 1);
                itens.setValorTotal(0.0);
                double valorTotal = itens.getValorTotal() + (itens.getQuantidade() * itens.getValorUnitario());
                itens.setValorTotal(converterValor(valorTotal));
                controle = 1;
                itens.setPedidos(pedido);
                break;
            }
        }
        if (controle == 0) {
            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setValorUnitario((double) produto.getValorVenda());
            item.setQuantidade(item.getQuantidade() + 1);
            double valorTotal = item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario());
            item.setValorTotal(converterValor(valorTotal));
            itensCompras.add(item);
            item.setPedidos(pedido);
        }
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - 1);
        if (produto.getQuantidadeEstoque() == 0) {
            produto.setDisponibilidade(DisponibilidadeProduto.INDISPONIVEL);
        }

        pedido.setItemPedido(itensCompras);
        pedido.setDataCompra(LocalDate.now());
        pedido.setValorTotal(calcularTotalPedidos(itensCompras, pedido));
        pedido.setTransacao(cliente.getTransacao());
        pedidoRepository.save(pedido);
        produtoRepository.save(produto);

    }

    public boolean finalizarCompra(Cliente cliente, Pedido pedido, List<ItemPedido> itensCompras, String formaPagmento) {
       if (pedido != null) {
           pedido.setTransacao(cliente.getTransacao());
           pedido.setStatusPedido(StatusPedido.CONCLUIDO);
           pedido.setFormaPagmento(formaPagmento);
           pedido.setDataCompra(LocalDate.now());
           Double valorTotal = calcularTotalPedidos(itensCompras, pedido);
           if (valorTotal != 0) {
               pedido.setValorTotal(valorTotal);
               for (ItemPedido i : itensCompras) {
                   i.setPedidos(pedido);
               }
               pedido.setItemPedido(itensCompras);
               pedidoRepository.save(pedido);
               return true;
           }
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

    public Pedido buscarPedido(Cliente cliente) {
        return pedidoRepository.getByTransacao(cliente.getTransacao());
    }

    public List<ItemPedido> listaPedido(Pedido pedido) {
        if (pedido == null) {
            return new ArrayList<>();
        }
        return itemPedidoRepository.findAllByIdPedido(pedido.getId());
    }

    private Pedido localizarPedido(Cliente cliente) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findByStatusPedido(StatusPedido.PROCESSANDO, cliente);
        Pedido pedido = null;
        List<ItemPedido> itensCompras = null;
        if (pedidoOptional.isPresent()) {
            pedido = pedidoOptional.get();
            itensCompras = listaPedido(pedido);
            pedido.setItemPedido(itensCompras);
            return pedido;
        } else {
            pedido = new Pedido();
            itensCompras = new ArrayList<>();
            pedido.setItemPedido(itensCompras);
            pedido.setStatusPedido(StatusPedido.PROCESSANDO);
            return pedido;
        }
    }

/*    private List<ItemPedido> x(List<ItemPedido> itensCompras ) {
        int controle = 0;
        for (ItemPedido itens : itensCompras) {
            if (itens.getProduto().getId().equals(produto.getId())) {
                itens.setQuantidade(itens.getQuantidade() + 1);
                itens.setValorTotal(0.0);
                double valorTotal = itens.getValorTotal() + (itens.getQuantidade() * itens.getValorUnitario());
                itens.setValorTotal(converterValor(valorTotal));
                controle = 1;
                itens.setPedidos(pedido);
                break;
            }
        }
        if (controle == 0) {
            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setValorUnitario((double) produto.getValorVenda());
            item.setQuantidade(item.getQuantidade() + 1);
            double valorTotal = item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario());
            item.setValorTotal(converterValor(valorTotal));
            itensCompras.add(item);
            item.setPedidos(pedido);
        }
    }*/
 }

