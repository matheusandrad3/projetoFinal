package br.com.araujo.loja.repository;

import br.com.araujo.loja.models.ItensCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItensCompraRepository extends JpaRepository<ItensCompra, Long> {
}
