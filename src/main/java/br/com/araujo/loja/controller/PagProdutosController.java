package br.com.araujo.loja.controller;

import br.com.araujo.loja.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PagProdutosController {


    @Autowired
    private ProdutoRepository repository;

    @GetMapping("/produtos")
    public ModelAndView index (){
        ModelAndView model = new ModelAndView("/index");
        model.addObject("listaProdutos", repository.findAll());
        return model;
    }

}
