package app.repository;

import app.model.Pedidos;
import app.model.Teste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import java.util.List;

public interface PedidosRepository extends JpaRepository<Pedidos, Long> {

}
