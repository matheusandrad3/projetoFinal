package app.repository;

import app.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByRg(String rg);

    @Query("select f from Funcionario f where f.email = :username")
    Funcionario getFuncionarioByEmail(@Param("username") String username);
}
