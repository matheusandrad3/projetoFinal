package app.dto.enderecoDto;

import app.model.enums.UnidadeFederativa;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EnderecoRequestDTO {

    private Long id;
    @Column(name = "CEP")
    @Size(min = 9, max = 9, message = "O CEP deve estar no formato 99999-999")
    @NotBlank(message = "O cep não pode ser nulo ou vazio!")
    private String cep;

    @Column(name = "CIDADE")
    @Size(max = 100, message = "A Cidade deve conter no máximo 100 caracter!")
    private String cidade;

    @Column(name = "BAIRRO")
    @Size(max = 100, message = "O Bairro deve conter no máximo 100 caracter!")
    private String bairro;

    @Column(name = "RUA")
    @Size(max = 100, message = "A Rua deve conter no máximo 100 caracter!")
    private String rua;

    @Enumerated(EnumType.STRING)
    @Column(name = "UF")
    @Size(max = 2, min = 2, message = "A UF deve conter no máximo 2 caracteres")
    private UnidadeFederativa uf;


    @Size(min = 1, max = 10, message = "O numero deve ter no mínimo 1 e no máximo 10 caracteres!")
    @NotBlank(message = "O número não pode ser nulo ou vazio!")
    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "COMPLEMENTO")
    @Size(max = 100, message = "O Complemento deve conter no máximo 100 caracter!")
    private String complemento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public UnidadeFederativa getUf() {
        return uf;
    }

    public void setUf(UnidadeFederativa uf) {
        this.uf = uf;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

}