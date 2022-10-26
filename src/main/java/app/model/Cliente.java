package app.model;

import javax.persistence.*;

@Entity
@Table(name = "TB_CLIENTE")
public class Cliente extends Pessoa{

    @ManyToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.PERSIST)
    private Transacao transacao;

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Transacao getTransacao() {
        return transacao;
    }

    public void setTransacao(Transacao transacao) {
        this.transacao = transacao;
    }
}