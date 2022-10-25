package app.dto.clienteDto;

import app.dto.enderecoDto.EnderecoRequestDTO;
import app.dto.pessoaDto.PessoaRequestDTO;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ClienteRequestDTO extends PessoaRequestDTO {

    private EnderecoRequestDTO endereco;

    public EnderecoRequestDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoRequestDTO endereco) {
        this.endereco = endereco;
    }
}