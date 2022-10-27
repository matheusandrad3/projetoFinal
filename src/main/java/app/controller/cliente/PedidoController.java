package app.controller.cliente;

import app.model.Cliente;
import app.model.ItemPedido;
import app.model.Pedido;
import app.model.Produto;
import app.service.ClienteService;
import app.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cliente/carrinho")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteService clienteService;


    @GetMapping()
    public ModelAndView chamarCarrinho() {
        ModelAndView model = new ModelAndView("/cliente/carrinho");
        Cliente cliente = clienteService.bucarUsuario();
        Pedido pedido = pedidoService.buscarPedido(cliente);
        if (pedido == null) {
            pedido = new Pedido();
        }
        List<ItemPedido> itensCompras = pedidoService.listaPedido(pedido);
        Produto produto = new Produto();
        Pedido compra = new Pedido();
        compra.setValorTotal(pedidoService.calcularTotalPedidos(itensCompras, pedido));
        model.addObject("compra", compra);
        model.addObject("listaItens", itensCompras);
        model.addObject("cliente", cliente);
        model.addObject("produto", produto);

        return model;
    }

    @PostMapping("/sucesso")
    public ModelAndView finalizarCompra(String formaPagmento) {
        ModelAndView model = new ModelAndView("/cliente/finalizarCompra");
        Cliente cliente = clienteService.bucarUsuario();
        Pedido pedido = pedidoService.buscarPedido(cliente);
        List<ItemPedido> itensCompras = pedidoService.listaPedido(pedido);
        model.addObject("compra", pedido);
        model.addObject("listaItens", itensCompras);
        model.addObject("cliente", cliente);

        if(!pedidoService.finalizarCompra(cliente, pedido, itensCompras, formaPagmento)){
            return chamarCarrinho();
        };

        return model;
    }

    @PostMapping("/comprar/{id}")
    public String addCarrinho(@PathVariable Long id) {
        Cliente cliente = clienteService.bucarUsuario();
        if (cliente == null) {
            return "/cliente/login";
        }
        pedidoService.addCarrinho(id);
        return "redirect:/produtos";
    }

    @PutMapping("/alterarQuantidade/{id}/{acao}")
    public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
        pedidoService.alterarQuantidade(id, acao);
        return "redirect:/cliente/carrinho";

    }

    @DeleteMapping("/remover/{id}")
    public String removerCarrinho(@PathVariable Long id) {
        Cliente cliente = clienteService.bucarUsuario();
        Pedido pedido = pedidoService.buscarPedido(cliente);
        pedidoService.removerCarrinho(id, pedido);
        return "redirect:/cliente/carrinho";
    }
}
