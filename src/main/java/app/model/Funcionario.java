package app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_funcionario")
public class Funcionario extends Pessoa{

    @NotBlank(message = "O Cargo não pode ser vazio!")
    @Column(name = "CARGO")
    @Size(max = 50, message = "O campo cargo deve conter no máximo 50 caracter!")
    String cargo;

    @Column(name = "salario")
    @NotNull(message = "O campo salário não pode ser vazio")
    Double salario;

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }
}
