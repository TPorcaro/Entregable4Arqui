package tporcaro.despensa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tporcaro.despensa.DTO.ClienteConCompras;
import tporcaro.despensa.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	@Query("SELECT new tporcaro.despensa.DTO.ClienteConCompras(c.cliente,SUM(c.precio_total+0)) "
			+ "FROM Compra c GROUP BY(c.cliente) ORDER BY (SUM(c.precio_total+0)) DESC")
	List<ClienteConCompras> generarReporteCliente();

	@Query("SELECT c FROM Cliente c WHERE c.nombre = :name")
	Cliente getByName(String name);

}
