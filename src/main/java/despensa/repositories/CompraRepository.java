package despensa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import despensa.DTO.Venta;
import despensa.entities.Compra;

public interface CompraRepository extends JpaRepository<Compra, Integer> {

	//PREGUNTAR URGENTE
	@Query("SELECT c FROM Compra c WHERE (c.cliente = :clientID "
			+ "AND YEAR(c.fecha_compra) = YEAR(CURRENT_DATE)"
			+ "AND MONTH(c.fecha_compra) = MONTH(CURRENT_DATE)"
			+ "AND DAY(c.fecha_compra) = DAY(CURRENT_DATE))")
	List<Compra> getComprasByCliente(int clientID);

	@Query("SELECT new despensa.DTO.Venta(c.fecha_compra,sum(c.precio_total+0)) FROM Compra c "
			+ "GROUP BY(date(c.fecha_compra))")
	List<Venta> generarReporteVentas();

}
