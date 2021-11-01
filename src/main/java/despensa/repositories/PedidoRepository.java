package despensa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import despensa.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
