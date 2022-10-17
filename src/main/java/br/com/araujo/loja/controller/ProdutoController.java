package br.com.araujo.loja.controller;

import br.com.araujo.loja.models.Produto;
import br.com.araujo.loja.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/administrativo/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/cadastro")
    public ModelAndView cadastrar(Produto produto) {
        ModelAndView model = new ModelAndView("administrativo/produtos/cadastro");
        model.addObject("produto", produto);
        return model;
    }

    @GetMapping("/lista")
    public ModelAndView listar() {
        ModelAndView model = new ModelAndView("administrativo/produtos/lista");
        model.addObject("listaProdutos", produtoRepository.findAll());
        return model;
    }

    @PutMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return cadastrar(produto.get());
    }

    @DeleteMapping("/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        produtoRepository.delete(produto.get());
        return listar();
    }


    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid Produto produto, BindingResult result){
        if(result.hasErrors()){
            return cadastrar(produto);
        }
        produtoRepository.saveAndFlush(produto);

        return cadastrar(new Produto());
    }


}
