package br.com.araujo.loja.repository;

import br.com.araujo.loja.models.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra,Long> {
}
