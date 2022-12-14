package app.controller.cliente;

import app.mapper.ProdutoMapper;
import app.model.Produto;
import app.repository.ProdutoRepository;
import app.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private ProdutoMapper mapper;

    @Autowired
    private ProdutoRepository repository;

    @GetMapping("/produtos")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("/index");
        model.addObject("listaProdutos", service.listarProdutosDisponivel());
        return model;
    }

    @GetMapping("/produtos/higiene")
    public ModelAndView buscarPorHigiene() {
        ModelAndView model = new ModelAndView("/cliente/produtos/higiene");
        model.addObject("listaProdutos", repository.findByCategoria("Higiene"));
        return model;
    }

    @GetMapping("/produtos/saude")
    public ModelAndView buscarPorSaude() {
        ModelAndView model = new ModelAndView("/cliente/produtos/saude");
        model.addObject("listaProdutos", repository.findByCategoria("Cuidado Saude"));
        return model;
    }

    @GetMapping("/produtos/nossas")
    public ModelAndView buscarPorNossas() {
        ModelAndView model = new ModelAndView("/cliente/produtos/nossas");
        model.addObject("listaProdutos", repository.findByCategoria("Nossas Marcas"));
        return model;
    }

    @GetMapping("/produtos/beleza")
    public ModelAndView buscarPorBeleza() {
        ModelAndView model = new ModelAndView("/cliente/produtos/beleza");
        model.addObject("listaProdutos", repository.findByCategoria("Beleza"));
        return model;
    }

    @GetMapping("/produtos/maisnossas")
    public ModelAndView buscarPorMaisNossasMarcas() {
        ModelAndView model = new ModelAndView("/cliente/produtos/maisNossas");
        model.addObject("listaProdutos", repository.findByCategoria("Mais Nossas Marcas"));
        return model;
    }

    @GetMapping("/produtos/destaque")
    public ModelAndView buscarPorDestaque() {
        ModelAndView model = new ModelAndView("/cliente/produtos/destaque");
        model.addObject("listaProdutos", repository.findByCategoria("Destaques Semana"));
        return model;
    }
    @PostMapping("/produtos/pesquisarnome")
    public ModelAndView buscarPorNome(@RequestParam("nomepesquisa") String nomePesquisa) {
        ModelAndView model = new ModelAndView("/cliente/produtos/nome");
        model.addObject("listaProdutos", repository.findByName(nomePesquisa));
        model.addObject("listaProdutobj", new Produto());
        return model;
    }
}
