package despensa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import despensa.entities.Compra;

public interface CompraRepository extends JpaRepository<Compra, Integer> {

}
