package app.mapper;

import app.dto.funcionarioDto.FuncionarioRequestDTO;
import app.model.Funcionario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FuncionarioMapper {

    Funcionario toFuncionario(FuncionarioRequestDTO dto);

    FuncionarioRequestDTO toFuncionarioRequestDto(Funcionario funcionario);
}
