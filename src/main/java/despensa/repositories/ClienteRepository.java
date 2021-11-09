package despensa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import despensa.DTO.ClienteConCompras;
import despensa.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	@Query("SELECT new despensa.DTO.ClienteConCompras(c.cliente,SUM(c.precio_total))"
			+ "FROM Compra c")
	List<ClienteConCompras> generarReporteCliente();


}
