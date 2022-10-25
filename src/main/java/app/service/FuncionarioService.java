package app.service;

import app.exeception.AraujoExeception;
import app.model.Cliente;
import app.model.Funcionario;
import app.model.Produto;
import app.model.Transacao;
import app.repository.ClienteRepository;
import app.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    public Funcionario cadastarFuncionario(Funcionario funcionario) {
        funcionario.setSenha(new BCryptPasswordEncoder().encode(funcionario.getSenha()));
        validarCpf(funcionario.getCpf());
        validarRg(funcionario.getRg());

        return repository.save(funcionario);
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

    public Funcionario bucarUsuario() {
        Authentication auntenticado = SecurityContextHolder.getContext().getAuthentication();
        Funcionario funcionario = null;
        if (!(auntenticado instanceof AnonymousAuthenticationToken)) {
            String email = auntenticado.getName();
            funcionario = repository.getFuncionarioByEmail(email);
        }
        return funcionario;
    }

    public List<Funcionario> listarFuncionarios() {
        return repository.findAll();
    }

    public Optional<Funcionario> buscarFuncionario(Long id) {
        return repository.findById(id);
    }

    public void deletarFuncionario(Long id) { repository.deleteById(id); }

    public void atualizarFuncionario(Funcionario funcionario) {
        funcionario.setSenha(new BCryptPasswordEncoder().encode(funcionario.getSenha()));
        repository.save(funcionario);
    }
}
