package app.dto.produtoDto;


import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProdutoRequestDTO {


    private Long id;
    @Column(name = "Nome")
    @Size(max = 100, message = "O campo nome deve conter no máximo 100 caracteres!")
    @NotBlank(message = "O campo Nome não pode ser nulo ou vazio!")
    private String nome;

    @Column(name = "descricao")
    @NotBlank(message = "O campo descrição não pode ser nulo ou vazio!")
    private String descricao;
    @Column(name = "valor_unitario")
    @NotNull(message = "O campo valor unitário não pode ser vazio")
    private Double valorVenda;
    private String categoria;
    private String marca;
    private Double quantidadeEstoque;

    @Column(name = "imagem")
    @NotBlank(message = "O campo imagem não pode ser nulo ou vazio!")
    private String UrlImagem;

    public String getUrlImagem() {
        return UrlImagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUrlImagem(String urlImagem) {
        UrlImagem = urlImagem;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Double quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}


