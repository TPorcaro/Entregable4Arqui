package tporcaro.despensa;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import tporcaro.despensa.entities.Compra;
import tporcaro.despensa.entities.Pedido;
import tporcaro.despensa.services.PedidoService;
import tporcaro.despensa.services.ProductoService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class PedidoTests {

	@Autowired
	private ProductoService productoService;
	@Autowired
	private PedidoService pedidoService;
	
	@Test
	@Order(1)
	void getPedidoByID() {
		Assertions.assertEquals(pedidoService.getById(2).get().getId(), 2);
	}
	@Test
	@Order(2)
	void updatePedido() {
		Pedido pedido = new Pedido(productoService.getById(1).get(),5);
		pedido.setId(2);
		Assertions.assertEquals(pedidoService.updatePedido(pedido), true);
		Assertions.assertEquals(pedidoService.getById(2).get(), pedido);
	}
	@Test
	@Order(3)
	void addPedido() {
		Pedido pedido = new Pedido(productoService.getById(4).get(),5);
		Assertions.assertEquals(pedidoService.addPedido(pedido), true);
	}
	@Test
	@Order(4)
	void getAll() {
		Page<Pedido> page = pedidoService.getAll(0, 10);
		List<Pedido> pedidos = page.getContent();
		for (Pedido pedido : pedidos) {
			Assertions.assertEquals(pedido.getClass(), Pedido.class);
		}
	}
}
