package tporcaro.despensa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tporcaro.despensa.DTO.Venta;
import tporcaro.despensa.entities.Cliente;
import tporcaro.despensa.entities.Compra;

public interface CompraRepository extends JpaRepository<Compra, Integer> {

	@Query("SELECT c FROM Compra c WHERE (c.cliente = :client "
			+ "AND YEAR(c.fecha_compra) = YEAR(CURRENT_DATE)"
			+ "AND MONTH(c.fecha_compra) = MONTH(CURRENT_DATE)"
			+ "AND DAY(c.fecha_compra) = DAY(CURRENT_DATE))")
	List<Compra> getComprasByCliente(Cliente client);

	@Query("SELECT new tporcaro.despensa.DTO.Venta(c.fecha_compra,sum(c.precio_total+0)) FROM Compra c "
			+ "GROUP BY(c.fecha_compra) ORDER BY (c.fecha_compra)")
	List<Venta> generarReporteVentas();

}
