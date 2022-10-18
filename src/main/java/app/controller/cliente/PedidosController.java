package app.controller.cliente;

import app.dto.pedidosDto.PedidosRequestDTO;
import app.dto.pedidosDto.PedidosResponseDTO;
import app.mapper.PedidosMapper;
import app.model.Cliente;
import app.model.ItemPedido;
import app.model.Pedidos;
import app.model.Produto;
import app.repository.ClienteRepository;
import app.repository.PedidosRepository;
import app.repository.ProdutoRepository;
import app.service.ClienteService;
import app.service.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    private PedidosRepository pedidosRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    @GetMapping()
    public ModelAndView chamarCarrinho() {
        ModelAndView model = new ModelAndView("/cliente/carrinho");
        Cliente cliente = clienteService.bucarUsuario();
        List<ItemPedido> itensCompras = service.intensPedidos();
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
        List<ItemPedido> itensCompras = service.intensPedidos();
        Pedidos compra = new Pedidos();
        compra.setValorTotal(service.calcularTotalPedidos());
        model.addObject("compra", compra);
        model.addObject("listaItens", itensCompras);
        model.addObject("cliente", cliente);
        compra.setCliente(cliente);
        compra.setFormaPagmento(formaPagmento);
        pedidosRepository.saveAndFlush(compra);
        for(ItemPedido i:itensCompras){
            i.setPedidos(compra);

        }
        pedidosRepository.save(compra);
        itensCompras = new ArrayList<>();
        compra = new Pedidos();
        return model;
    }

    @PostMapping("/comprar/{id}")
    public String addCarrinho(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        Produto produto1 = produto.get();
        List<ItemPedido> itensCompras = service.intensPedidos();
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
