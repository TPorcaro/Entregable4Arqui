package tporcaro.despensa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import tporcaro.despensa.entities.Cliente;
import tporcaro.despensa.entities.Compra;
import tporcaro.despensa.entities.Pedido;
import tporcaro.despensa.entities.Producto;
import tporcaro.despensa.services.ClienteService;
import tporcaro.despensa.services.CompraService;
import tporcaro.despensa.services.PedidoService;
import tporcaro.despensa.services.ProductoService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
@SpringBootTest
class ProductoTests {

	@Autowired
	private ProductoService productoService;
	@Autowired
	private CompraService compraService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private ClienteService clienteService;
	
	
	@Test
	@Order(1)
	void addProducto() {
		Producto producto = new Producto("ProductoTest",500,10);
		Assertions.assertEquals(productoService.addProducto(producto), true);
		Assertions.assertEquals(productoService.getByName(producto.getNombre()).getNombre(), producto.getNombre());
	}
	
	@Test
	@Order(2)
	void getProductoByID() {
		Assertions.assertEquals(productoService.getById(1).get().getId(), 1);
	}
	@Test
	@Order(3)
	void updateProducto() {
		Producto producto = new Producto("nuevo nombre", 100,15);
		producto.setId(1);
		Assertions.assertEquals(productoService.updateProducto(producto), true);
		Assertions.assertEquals(productoService.getById(1).get().getNombre(), producto.getNombre());
	}
	@Test
	@Order(4)
	void getAll() {
		Page<Producto> page = productoService.getAll(0, 10);
		List<Producto> productos = page.getContent();
		for (Producto producto : productos) {
			Assertions.assertEquals(producto.getClass(), Producto.class);
		}
	}
	@Test
	@Order(5)
	void ProductoMasVendido() {
		compraService.vaciarCompra();
		pedidoService.vaciarPedido();
		Producto producto1 = productoService.getById(1).get();
		Producto producto2 = productoService.getById(4).get();
		Pedido pedido1 = new Pedido(producto1, 50);
		List<Pedido> listPedido1 = new ArrayList<Pedido>(); 
		listPedido1.add(pedido1);
		Pedido pedido2 = new Pedido(producto2, 5);
		List<Pedido> listPedido2 = new ArrayList<Pedido>(); 
		listPedido2.add(pedido2);
		pedidoService.addPedido(pedido1);
		pedidoService.addPedido(pedido2);
		Cliente cliente1 = clienteService.getById(3).get();
		Cliente cliente2 = clienteService.getById(6).get();
		Long max =0L;
        Long min =100000000000L;
        Random r = new Random();
        Long randomLong=(r.nextLong() % (max - min)) + min;
        Date dt =new Date(randomLong);
		Compra compra1 = new Compra(cliente1,dt, listPedido1);
		Compra compra2 = new Compra(cliente2,dt, listPedido2);
		compraService.addCompra(compra1);
		compraService.addCompra(compra2);
		Producto masVendidoProducto = productoService.getProductoMasVendido();
		Assertions.assertEquals(masVendidoProducto, producto1);
	}

}
