package app.mapper;

import app.dto.produtoDto.ProdutoRequestDTO;
import app.model.Produto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    Produto toProduto(ProdutoRequestDTO dto);

    ProdutoRequestDTO toProdutoRequestDTO(Produto produto);
}