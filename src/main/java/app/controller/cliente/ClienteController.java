package app.controller.cliente;

import app.dto.clienteDto.ClienteRequestDTO;
import app.mapper.ClienteMapper;
import app.model.Cliente;
import app.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Autowired
    private ClienteMapper mapper;


    @GetMapping("/cadastro")
    public ModelAndView cadastro(ClienteRequestDTO cliente) {
        ModelAndView model = new ModelAndView("/cliente/cadastro");
        model.addObject("cliente", cliente);
        model.addObject("endereco", cliente.getEndereco());
        return model;
    }

    @GetMapping("/login")
    public ModelAndView login(ClienteRequestDTO cliente) {
        ModelAndView model = new ModelAndView("/cliente/login");
        return model;
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrar(ClienteRequestDTO dto) {
        service.cadastarCliente(mapper.toCliente(dto));
        ModelAndView model = new ModelAndView("/cliente/login");
        return model;

    }
}


