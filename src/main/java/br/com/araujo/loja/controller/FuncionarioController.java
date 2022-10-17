package br.com.araujo.loja.controller;

import br.com.araujo.loja.models.Funcionario;
import br.com.araujo.loja.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/administrativo/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/cadastro")
    public ModelAndView cadastrar(Funcionario funcionario) {
        ModelAndView model = new ModelAndView("administrativo/funcionarios/cadastro");
        model.addObject("funcionario", funcionario);
        return model;
    }

    @GetMapping("/lista")
    public ModelAndView listar(){
        ModelAndView model = new ModelAndView("administrativo/funcionarios/lista");
        model.addObject("listaFuncionarios", funcionarioRepository.findAll());

        return model;
    }

    @PutMapping("/editar/{id}")
    public ModelAndView editarCadastro (@PathVariable("id") Long id){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        return cadastrar(funcionario.get());

    }
    @DeleteMapping("/remover/{id}")
    public ModelAndView excluirCadastro (@PathVariable("id") Long id){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        funcionarioRepository.delete(funcionario.get());
        return listar() ;

    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid Funcionario funcionario, BindingResult result){
        if(result.hasErrors()){
            return cadastrar(funcionario);
        }
        funcionarioRepository.saveAndFlush(funcionario);

        return cadastrar(new Funcionario());
    }


}
