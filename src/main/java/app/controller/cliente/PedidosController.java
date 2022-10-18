package app.controller.cliente;

import app.model.Cliente;
import app.model.ItemPedido;
import app.model.Pedidos;
import app.model.Produto;
import app.repository.PedidosRepository;
import app.repository.ProdutoRepository;
import app.service.ClienteService;
import app.service.PedidosService;
import app.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cliente/carrinho")
public class PedidosController {

    @Autowired
    private PedidosService service;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    @GetMapping()
    public ModelAndView chamarCarrinho() {
        ModelAndView model = new ModelAndView("/cliente/carrinho");
        Cliente cliente = clienteService.bucarUsuario();
        List<ItemPedido> itensCompras = service.getItensCompras();
        Pedidos compra = new Pedidos();
        compra.setValorTotal(service.calcularTotalPedidos());
        model.addObject("compra", compra);
        model.addObject("listaItens", itensCompras);
        model.addObject("cliente", cliente);
        return model;
    }

    @PostMapping("/sucesso")
    public ModelAndView finalizarCompra(String formaPagmento) {
        Cliente cliente = clienteService.bucarUsuario();
        ModelAndView model = new ModelAndView("/cliente/finalizarCompra");
        List<ItemPedido> itensCompras = service.getItensCompras();
        Pedidos compra = new Pedidos();
        compra.setValorTotal(service.calcularTotalPedidos());
        model.addObject("compra", compra);
        model.addObject("listaItens", itensCompras);
        model.addObject("cliente", cliente);
        compra.setCliente(cliente);
        compra.setFormaPagmento(formaPagmento);
        compra.setDataCompra(LocalDate.now());
        pedidosRepository.saveAndFlush(compra);
        for(ItemPedido i:itensCompras){
            i.setPedidos(compra);
            produtoService.consumirEstoque(i.getProduto().getId(), i.getQuantidade());
        }
        pedidosRepository.save(compra);

        service.setItensCompras(new ArrayList<>());
        service.setCompra(new Pedidos());
        return model;
    }

    @PostMapping("/comprar/{id}")
    public String addCarrinho(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        Produto produto1 = produto.get();
        List<ItemPedido> itensCompras = service.getItensCompras();
        int controle = 0;
        for (ItemPedido itens : itensCompras) {
            if (itens.getProduto().getId().equals(produto1.getId())) {
                itens.setQuantidade(itens.getQuantidade() + 1);
                itens.setValorTotal(0.);
                itens.setValorTotal(itens.getValorTotal()+(itens.getQuantidade() * itens.getValorUnitario()));
                controle = 1;
                break;
            }
        }
        if (controle == 0) {
            ItemPedido item = new ItemPedido();
            item.setProduto(produto1);
            item.setValorUnitario((double) produto1.getValorVenda());
            item.setQuantidade(item.getQuantidade() + 1);
            item.setValorTotal(item.getValorTotal()+(item.getQuantidade() * item.getValorUnitario()));
            itensCompras.add(item);
        }

        return "redirect:/produtos";
    }
}
