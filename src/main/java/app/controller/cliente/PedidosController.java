package app.controller.cliente;

import app.model.Cliente;
import app.model.ItemPedido;
import app.model.Pedidos;
import app.repository.PedidosRepository;
import app.repository.ProdutoRepository;
import app.service.ClienteService;
import app.service.PedidosService;
import app.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cliente/carrinho")
public class PedidosController {

    @Autowired
    private PedidosService pedidosService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping()
    public ModelAndView chamarCarrinho() {
        ModelAndView model = new ModelAndView("/cliente/carrinho");
        Cliente cliente = clienteService.bucarUsuario();
        List<ItemPedido> itensCompras = pedidosService.getItensCompras();
        Pedidos compra = new Pedidos();
        compra.setValorTotal(pedidosService.calcularTotalPedidos());
        model.addObject("compra", compra);
        model.addObject("listaItens", itensCompras);
        model.addObject("cliente", cliente);
        return model;
    }

    @PostMapping("/sucesso")
    public ModelAndView finalizarCompra(String formaPagmento) {
        ModelAndView model = new ModelAndView("/cliente/finalizarCompra");
        Cliente cliente = clienteService.bucarUsuario();
        Pedidos compra = new Pedidos();
        List<ItemPedido> itensCompras = pedidosService.getItensCompras();

        model.addObject("compra", compra);
        model.addObject("listaItens", itensCompras);
        model.addObject("cliente", cliente);

        produtoService.finalizarCompra(cliente, compra, itensCompras, formaPagmento);

        pedidosService.setItensCompras(new ArrayList<>());
        pedidosService.setCompra(new Pedidos());
        return model;
    }

    @PostMapping("/comprar/{id}")
    public String addCarrinho(@PathVariable Long id) {
        produtoService.addCarrinho(id);
        return "redirect:/produtos";
    }

    @PutMapping("/alterarQuantidade/{id}/{acao}")
    public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
        produtoService.alterarQuantidade(id,acao);
        return "redirect:/cliente/carrinho";
    }

    @DeleteMapping("/remover/{id}")
    public String removerCarrinho(@PathVariable Long id) {
        produtoService.removerCarrinho(id);
        return "redirect:/cliente/carrinho";
    }
}
