package app.controller.cliente;

import app.model.Cliente;
import app.model.ItemPedido;
import app.model.Pedido;
import app.service.ClienteService;
import app.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

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
        List<ItemPedido> itensCompras = pedidoService.getItensCompras();
        Pedido compra = new Pedido();
        compra.setValorTotal(pedidoService.calcularTotalPedidos());
        model.addObject("compra", compra);
        model.addObject("listaItens", itensCompras);
        model.addObject("cliente", cliente);
        return model;
    }

    @PostMapping("/sucesso")
    public ModelAndView finalizarCompra(String formaPagmento) {
        ModelAndView model = new ModelAndView("/cliente/finalizarCompra");
        Cliente cliente = clienteService.bucarUsuario();
        Pedido compra = new Pedido();
        List<ItemPedido> itensCompras = pedidoService.getItensCompras();

        model.addObject("compra", compra);
        model.addObject("listaItens", itensCompras);
        model.addObject("cliente", cliente);

        if(!pedidoService.finalizarCompra(cliente, compra, itensCompras, formaPagmento)){
            return chamarCarrinho();
        };

        pedidoService.setItensCompras(new ArrayList<>());
        pedidoService.setCompra(new Pedido());
        return model;
    }

    @PostMapping("/comprar/{id}")
    public String addCarrinho(@PathVariable Long id) {
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
        pedidoService.removerCarrinho(id);
        return "redirect:/cliente/carrinho";
    }
}
