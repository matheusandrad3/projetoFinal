package app.model;

import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Users")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O Nome não pode ser vazio!")
    @Column(name = "NOME")
    @Size(max = 100, message = "O Nome deve conter no máximo 100 caracter!")
    private String nome;

    @NotBlank(message = "A Senha não pode ser vazia!")
    @Column(name = "password")
    @Size(min = 6, message = "A Senha deve conter no mínimo 6 caracter!")
    private String senha;

    @Column(name = "TELEFONE")
    private String telefone;

    @CPF(message = "CPF inválido!")
    @Column(name = "CPF", unique = true)
    @Size(min = 11, max = 11, message = "O CPF deve conter 11 digitos!")
    private String cpf;

    @NotBlank(message = "O E-mail não pode ser vazio!")
    @Column(name = "username")
    @Size(max = 100, message = "O E-mail deve conter no máximo 100 caracter!")
    private String email;

    @NotBlank(message = "O RG não pode ser vazio!")
    @Column(name = "RG", unique = true)
    private String rg;

    @ManyToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.PERSIST)
    private Transacao transacao;

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

    public Endereco getEnderecos() {
        return endereco;
    }

    public void setEnderecos(Endereco endereco) {
        this.endereco = endereco;
    }

    public Transacao getTransacao() {
        return transacao;
    }

    public void setTransacao(Transacao transacao) {
        this.transacao = transacao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
