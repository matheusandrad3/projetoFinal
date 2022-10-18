package app.mapper;

import app.dto.clienteDto.ClienteLoginResponseDTO;
import org.mapstruct.Mapper;

import app.dto.clienteDto.ClienteRequestDTO;
import app.dto.clienteDto.ClienteResponseDTO;
import app.model.Cliente;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

	@Mapping(target = "enderecos", source = "endereco")
	@Mapping(target = "enderecos.uf", source = "endereco.uf")
	Cliente toCliente(ClienteRequestDTO dto);

	ClienteResponseDTO toClienteResponseDTO(Cliente cli);

	ClienteLoginResponseDTO toClienteLoginResponseDTO(Cliente cli);


}
