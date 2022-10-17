package br.com.araujo.loja.controller;

import br.com.araujo.loja.models.Cliente;
import br.com.araujo.loja.models.Endereco;
import br.com.araujo.loja.repository.EnderecoRepository;
import br.com.araujo.loja.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoRepository cidadeRepository;

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Cliente cliente, Endereco endereco){
        ModelAndView model = new ModelAndView("/cliente/cadastro");
        model.addObject("cliente", cliente);
        model.addObject("endereco", endereco);
        return model;
    }
    @GetMapping("/login")
    public ModelAndView login(Cliente cliente){
        ModelAndView model = new ModelAndView("/cliente/login");
        return model;
    }

    @PutMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Cliente>cliente = clienteRepository.findById(id);
        Optional<Endereco>endereco = enderecoRepository.findById(id);
        return cadastro(cliente.get(), endereco.get());
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrar (@Valid Cliente cliente, @Valid Endereco endereco, BindingResult result){
      if (result.hasErrors()){
          return cadastro(cliente, endereco);
      }
      cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
      endereco.setCidade(endereco.getCidade());
      clienteRepository.saveAndFlush(cliente);
      enderecoRepository.saveAndFlush(endereco);
      return cadastro(new Cliente(), new Endereco());
    }
}
