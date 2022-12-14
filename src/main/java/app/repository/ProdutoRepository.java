package app.repository;

import app.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("select p from Produto p where p.categoria = :categoria and p.disponibilidade ='DISPONIVEL'")
    List<Produto> findByCategoria(@Param("categoria") String categoria);

    @Query(value = "select * from TB_PRODUTO where (nome like :keyword% or categoria like :keyword% or marca like :keyword%) and disponibilidade = 'DISPONIVEL'",nativeQuery=true)
    List<Produto> findByName(@Param("keyword") String nome);

    @Query("SELECT p.quantidadeEstoque FROM Produto p WHERE p.id = :id")
    Integer getQuantidadeEstoqueById(Long id);

    @Query("SELECT p FROM Produto p WHERE p.disponibilidade = 'DISPONIVEL'")
    List<Produto> findAllDisponivel();
}
