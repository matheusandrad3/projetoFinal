package br.com.araujo.loja.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class Cliente implements Serializable {

    public Cliente(){
        super();
    }

    @Id
    @Column(name = "username")
    private String email;

    @Column(name = "password")
    private String senha;
    private String nome;

    private boolean enabled = true;
    private String cpf;
    private String sigla;

    private String telefone;

    private String rg;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Endereco endereco;


    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
