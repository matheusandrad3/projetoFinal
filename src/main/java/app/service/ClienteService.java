package app.service;

import app.exeception.AraujoExeception;
import app.model.Cliente;
import app.model.Transacao;
import app.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente cadastarCliente(Cliente cliente) {
        cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
        validarCpf(cliente.getCpf());
        validarRg(cliente.getRg());
        cliente.getEnderecos().setCliente(cliente);
        Transacao transacao = new Transacao();
        transacao.setCliente(cliente);
        cliente.setTransacao(transacao);
        return repository.save(cliente);
    }

    private void validarCpf(String cpf) {
        if (repository.existsByCpf(cpf)) {
            throw new AraujoExeception("CPF já cadastrado", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private void validarRg(String rg) {
        if (repository.existsByRg(rg)) {
            throw new AraujoExeception("RG já cadastrado", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public Cliente bucarUsuario() {
        Authentication auntenticado = SecurityContextHolder.getContext().getAuthentication();
        Cliente cliente = null;
        if (!(auntenticado instanceof AnonymousAuthenticationToken)) {
            String email = auntenticado.getName();
            cliente = repository.getClienteByEmail(email);
        }
        return cliente;
    }
}
