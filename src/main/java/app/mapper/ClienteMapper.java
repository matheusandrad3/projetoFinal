package app.mapper;

import app.dto.clienteDto.ClienteRequestDTO;
import app.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "enderecos", source = "endereco")
    @Mapping(target = "enderecos.uf", source = "endereco.uf")
    Cliente toCliente(ClienteRequestDTO dto);
}