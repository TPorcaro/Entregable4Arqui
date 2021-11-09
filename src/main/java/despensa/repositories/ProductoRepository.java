package despensa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import despensa.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}
