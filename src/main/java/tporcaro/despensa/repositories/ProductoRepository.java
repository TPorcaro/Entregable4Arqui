package tporcaro.despensa.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tporcaro.despensa.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

	@Query("SELECT p.producto FROM Compra c JOIN c.pedidos p JOIN p.producto pr "
			+ "GROUP BY(p.producto) ORDER BY SUM(p.cantidad) DESC")
	List<Producto> getProductoMasVendido();

	@Query("SELECT p FROM Producto p WHERE p.nombre = :name")
	Producto getByName(String name);
}
