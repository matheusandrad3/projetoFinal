package app.controller.administrativo;

import app.dto.produtoDto.ProdutoRequestDTO;
import app.mapper.ProdutoMapper;
import app.model.Produto;
import app.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/administrativo/produtos")
public class ProdutoAdministrativoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private ProdutoMapper mapper;

    @GetMapping("/cadastro")
    public ModelAndView cadastrar(ProdutoRequestDTO produto) {
        ModelAndView model = new ModelAndView("administrativo/produtos/cadastro");
        model.addObject("produto", produto);
        return model;
    }


    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid ProdutoRequestDTO produto, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(produto);
        }
        service.cadastrarProduto(mapper.toProduto(produto));
        return cadastrar(new ProdutoRequestDTO());
    }

    @GetMapping("/lista")
    public ModelAndView listar() {
        ModelAndView model = new ModelAndView("administrativo/produtos/lista");
        model.addObject("listaProdutos", service.listarProdutos());
        return model;
    }

    @DeleteMapping("/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        service.deletarProduto(id);
        return listar();
    }

    @PutMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Produto> produto = service.atualizarProduto(id);
        return cadastrar(mapper.toProdutoRequestDTO(produto.get()));
    }
}
