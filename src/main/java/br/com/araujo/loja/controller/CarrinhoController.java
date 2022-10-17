package br.com.araujo.loja.controller;

import br.com.araujo.loja.models.Cliente;
import br.com.araujo.loja.models.Compra;
import br.com.araujo.loja.models.ItensCompra;
import br.com.araujo.loja.models.Produto;
import br.com.araujo.loja.repository.ClienteRepository;
import br.com.araujo.loja.repository.CompraRepository;
import br.com.araujo.loja.repository.ItensCompraRepository;
import br.com.araujo.loja.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CarrinhoController {

    private List<ItensCompra> itensCompras = new ArrayList<ItensCompra>();
    private Compra compra = new Compra();
    private Cliente cliente;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ItensCompraRepository itensCompraRepository;

    private void calcularTotalCarrinho(){
        compra.setValorTotal(0.);
        for(ItensCompra item: itensCompras){
            compra.setValorTotal(item.getValorTotal()+compra.getValorTotal());
        }
    }

    @GetMapping()
    public ModelAndView chamarCarrinho() {
        ModelAndView model = new ModelAndView("/cliente/carrinho");
        bucarUsuario();
        calcularTotalCarrinho();
        model.addObject("compra", compra);
        model.addObject("listaItens", itensCompras);
        model.addObject("cliente", cliente);
        return model;
    }

    private void bucarUsuario(){
        Authentication auntenticado = SecurityContextHolder.getContext().getAuthentication();
        if (!(auntenticado instanceof AnonymousAuthenticationToken)){
            String email = auntenticado.getName();
            cliente = clienteRepository.findByEmail(email).get(0);
        }
    }



    @PostMapping("/sucesso")
    public ModelAndView finalizarCompra(String formaPagmento) {
        bucarUsuario();
        ModelAndView model = new ModelAndView("/cliente/finalizarCompra");
        calcularTotalCarrinho();
        model.addObject("compra", compra);
        model.addObject("listaItens", itensCompras);
        model.addObject("cliente", cliente);

        compra.setCliente(cliente);
        compra.setFormaPagmento(formaPagmento);
        compraRepository.saveAndFlush(compra);

        for(ItensCompra i:itensCompras){
            i.setCompra(compra);
            itensCompraRepository.saveAndFlush(i);
        }
        itensCompras = new ArrayList<>();
        compra = new Compra();
        return model;
    }

    @PutMapping("/alterarQuantidade/{id}/{acao}")
    public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
        for (ItensCompra itens : itensCompras) {
            if (itens.getProduto().getId().equals(id)){
                if(acao.equals(1)) {
                    itens.setQuantidade(itens.getQuantidade() + 1);
                    itens.setValorTotal(0.);
                    itens.setValorTotal(itens.getValorTotal()+(itens.getQuantidade() * itens.getValorUnitario()));

                } else if (acao == 0) {
                    itens.setQuantidade(itens.getQuantidade() - 1);
                    itens.setValorTotal(0.);
                    itens.setValorTotal(itens.getValorTotal()+(itens.getQuantidade() * itens.getValorUnitario()));
                }
                break;
            }
        }

            return "redirect:/cliente/carrinho";
    }


    @PostMapping("/comprar/{id}")
    public String addCarrinho(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        Produto produto1 = produto.get();

        int controle = 0;
        for (ItensCompra itens : itensCompras) {
            if (itens.getProduto().getId().equals(produto1.getId())) {
                itens.setQuantidade(itens.getQuantidade() + 1);
                itens.setValorTotal(0.);
                itens.setValorTotal(itens.getValorTotal()+(itens.getQuantidade() * itens.getValorUnitario()));
                controle = 1;
                break;
            }
        }
        if (controle == 0) {
            ItensCompra item = new ItensCompra();
            item.setProduto(produto1);
            item.setValorUnitario(produto1.getValorVenda());
            item.setQuantidade(item.getQuantidade() + 1);
            item.setValorTotal(item.getValorTotal()+(item.getQuantidade() * item.getValorUnitario()));
            itensCompras.add(item);
        }

        return "redirect:/produtos";
    }

    @DeleteMapping("/remover/{id}")
    public String removerCarrinho(@PathVariable Long id){

            for(ItensCompra itens : itensCompras){
                if(itens.getProduto().getId().equals(id)){
                    itensCompras.remove(itens);
                    break;
                }
            }
            return "redirect:/cliente/carrinho";

    }
}
