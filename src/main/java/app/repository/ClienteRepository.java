package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	boolean existsByCpf(String cpf);

	boolean existsByRg(String rg);

	boolean existsByEmail(String email);

	@Query(value = "SELECT senha FROM TB_CLIENTE WHERE email = :email", nativeQuery = true)
	String getSenhaByEmail(String email);

	@Query("select c from Cliente c where c.email = :username")
	Cliente getClienteByEmail(@Param("username") String username);

}
