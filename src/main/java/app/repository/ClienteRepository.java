package app.repository;

import app.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByRg(String rg);

    @Query("select c from Cliente c where c.email = :username")
    Cliente getClienteByEmail(@Param("username") String username);
}