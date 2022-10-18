package app.controller.cliente;

import app.mapper.ProdutoMapper;
import app.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private ProdutoMapper mapper;


    @GetMapping
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("/index");
        model.addObject("listaProdutos", service.listarProdutos());
        return model;
    }


}
