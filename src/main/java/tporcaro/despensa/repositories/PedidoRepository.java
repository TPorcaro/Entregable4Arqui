package tporcaro.despensa.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import tporcaro.despensa.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
}
