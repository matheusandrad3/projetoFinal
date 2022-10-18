package app.mapper;

import app.dto.produtoDto.ProdutoRequestDTO;
import app.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    //@Mapping(target = "categoria", source = "categoria")
    Produto toProduto(ProdutoRequestDTO dto);

  //  @Mapping(target = "categoria", source = "categoria")
    ProdutoRequestDTO toProdutoRequestDTO(Produto produto);

}
