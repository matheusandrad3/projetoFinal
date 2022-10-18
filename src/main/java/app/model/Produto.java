package app.model;

import app.model.enums.DisponibilidadeProduto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoria;

    @Column(name = "Nome")
    @Size(max = 100, message = "O campo nome deve conter no máximo 100 caracteres!")
    @NotBlank(message = "O campo Nome não pode ser nulo ou vazio!")
    private String nome;

    @Column(name = "quantidade")
    @NotNull(message = "O campo quantidade não pode ser nulo ou vazio!")
    private Integer quantidadeEstoque;

    @Column(name = "valor_unitario")
    @NotNull(message = "O campo valor unitário não pode ser vazio")
    private float valorVenda;

    private String marca;
    @Column(name = "descricao")
    @NotBlank(message = "O campo descrição não pode ser nulo ou vazio!")
    private String descricao;

    @Column(name = "imagem")
    @NotBlank(message = "O campo imagem não pode ser nulo ou vazio!")
    private String UrlImagem;

    @Enumerated(EnumType.STRING)
    @Column(name = "Disponibilidade")
    private DisponibilidadeProduto disponibilidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public float getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(float valorVenda) {
        this.valorVenda = valorVenda;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlImagem() {
        return UrlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        UrlImagem = urlImagem;
    }

    public DisponibilidadeProduto getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(DisponibilidadeProduto disponibilidade) {
        this.disponibilidade = disponibilidade;
    }
}
