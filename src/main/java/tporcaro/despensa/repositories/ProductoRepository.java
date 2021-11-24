package tporcaro.despensa.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tporcaro.despensa.entities.Producto;

/**
 * The Repository ProductoRepository.
 */
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

	/**
	 * Gets the most selled Producto.
	 *
	 * @return Producto
	 */
	@Query("SELECT p.producto FROM Compra c JOIN c.pedidos p JOIN p.producto pr "
			+ "GROUP BY(p.producto) ORDER BY SUM(p.cantidad) DESC")
	List<Producto> getProductoMasVendido();

	/**
	 * Gets Producto by name
	 *
	 * @param name the name
	 * @return Producto
	 */
	@Query("SELECT p FROM Producto p WHERE p.nombre = :name")
	Producto getByName(String name);
}
