package app.controller.administrativo;

import app.dto.clienteDto.ClienteRequestDTO;
import app.dto.funcionarioDto.FuncionarioRequestDTO;
import app.dto.produtoDto.ProdutoRequestDTO;
import app.mapper.FuncionarioMapper;
import app.model.Funcionario;
import app.model.Produto;
import app.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/administrativo/funcionarios")
public class FuncionarioController {

    @Autowired
    FuncionarioService service;

    @Autowired
    FuncionarioMapper mapper;

    @GetMapping("/login")
    public ModelAndView login(FuncionarioRequestDTO funcionario) {
        ModelAndView model = new ModelAndView("administrativo/funcionarios/login");
        return model;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastrar(FuncionarioRequestDTO funcionario) {
        ModelAndView model = new ModelAndView("administrativo/funcionarios/cadastro");
        model.addObject("funcionario", funcionario);
        return model;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid FuncionarioRequestDTO funcionario, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(funcionario);
        }

        if (funcionario.getId() == null){
            service.cadastarFuncionario(mapper.toFuncionario(funcionario));
            return cadastrar(new FuncionarioRequestDTO());
        }
        service.atualizarFuncionario(mapper.toFuncionario(funcionario));
        return cadastrar(new FuncionarioRequestDTO());
    }

    @GetMapping("/lista")
    public ModelAndView listar() {
        ModelAndView model = new ModelAndView("administrativo/funcionarios/lista");
        model.addObject("listaFuncionarios", service.listarFuncionarios());
        return model;
    }

    @PutMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = service.buscarFuncionario(id);
        return cadastrar(mapper.toFuncionarioRequestDto(funcionario.get()));
    }

    @DeleteMapping("/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        service.deletarFuncionario(id);
        return listar();
    }
}
