package app.repository;

import app.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("select p from Produto p where p.categoria = :categoria")
    List<Produto> findByCategoria(@Param("categoria") String categoria);

    @Query("SELECT p.quantidadeEstoque FROM Produto p WHERE p.id = :id")
    Integer getQuantidadeEstoqueById(Long id);
}
