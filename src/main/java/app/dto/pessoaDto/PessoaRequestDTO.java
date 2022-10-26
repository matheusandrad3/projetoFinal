package app.dto.pessoaDto;

import app.dto.enderecoDto.EnderecoRequestDTO;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public abstract class PessoaRequestDTO {

    private Long id;

    @NotBlank(message = "O Nome não pode ser vazio!")
    @Size(max = 100, message = "O Nome deve conter no máximo 100 caracter!")
    private String nome;

    @NotBlank(message = "A Senha não pode ser vazia!")
    @Size(min = 6, message = "A Senha deve conter no mínimo 6 caracter!")
    private String senha;

    private String telefone;

    @Size(min = 11, max = 11, message = "O CPF deve conter 11 digitos!")
    private String cpf;

    @NotBlank(message = "O E-mail não pode ser vazio!")
    @Size(max = 100, message = "O E-mail deve conter no máximo 100 caracter!")
    private String email;

    @NotBlank(message = "O RG não pode ser vazio!")
    private String rg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }
}