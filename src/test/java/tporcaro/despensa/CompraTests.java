package tporcaro.despensa;

import java.sql.Date;

import java.util.ArrayList;

import java.util.List;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import tporcaro.despensa.DTO.Venta;
import tporcaro.despensa.entities.Cliente;
import tporcaro.despensa.entities.Compra;
import tporcaro.despensa.entities.Pedido;
import tporcaro.despensa.entities.Producto;
import tporcaro.despensa.services.ClienteService;
import tporcaro.despensa.services.CompraService;
import tporcaro.despensa.services.PedidoService;
import tporcaro.despensa.services.ProductoService;

/**
 * The Class CompraTests.
 */
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CompraTests {

	/** The producto service. */
	@Autowired
	private ProductoService productoService;
	
	/** The compra service. */
	@Autowired
	private CompraService compraService;
	
	/** The pedido service. */
	@Autowired
	private PedidoService pedidoService;
	
	/** The cliente service. */
	@Autowired
	private ClienteService clienteService;
	
	/**
	 * Test Gets Compra by ID.
	 */
	@Test
	@Order(1)
	void getCompraByID() {
		Assertions.assertEquals(compraService.getById(152).get().getId(), 152);
	}
	
	/**
	 * Test Update Compra.
	 */
	@Test
	@Order(2)
	void updateCompra() {
		Pedido pedido = pedidoService.getById(2).get();
		List<Pedido> pedidos = new ArrayList<Pedido>();
		pedidos.add(pedido);
		Compra compra = new Compra(clienteService.getById(6).get(),Date.valueOf("2021-11-21"), pedidos);
		compra.setId(160);
		Assertions.assertEquals(compraService.updateCompra(compra), true);
		Assertions.assertEquals(compraService.getById(160).get(), compra);
	}
	
	/**
	 * Test Get all Compra.
	 */
	@Test
	@Order(3)
	void getAll() {
		Page<Compra> page = compraService.getAll(0, 10);
		List<Compra> compras = page.getContent();
		for (Compra compra : compras) {
			Assertions.assertEquals(compra.getClass(), Compra.class);
		}
	}
	
	/**
	 * Test Delete Compra.
	 */
	@Test
	@Order(4)
	void deleteCompra() {
		Compra aBorrarCompra = compraService.getById(152).get();
		Assertions.assertEquals(compraService.removeCompra(aBorrarCompra), true);
	}
	
	/**
	 * Test Adds Compra.
	 */
	@Test
	@Order(5)
	void addCompra() {
		compraService.vaciarCompra();
		pedidoService.vaciarPedido();
		Producto producto1 = productoService.getById(1).get();
		Pedido pedido1 = new Pedido(producto1, 50);
		List<Pedido> listPedido1 = new ArrayList<Pedido>(); 
		listPedido1.add(pedido1);
		pedidoService.addPedido(pedido1);
		Cliente cliente1 = clienteService.getById(3).get();
		Compra compra1 = new Compra(cliente1,Date.valueOf("2021-11-21"), listPedido1);
		Compra compra2 = new Compra(cliente1,Date.valueOf("2021-11-21"), listPedido1);
		Compra compra3 = new Compra(cliente1,Date.valueOf("2021-11-21"), listPedido1);
		Compra compra4 = new Compra(cliente1,Date.valueOf("2021-11-21"), listPedido1);
		Assertions.assertEquals(compraService.addCompra(compra1), true);
		Assertions.assertEquals(compraService.addCompra(compra2), true);
		Assertions.assertEquals(compraService.addCompra(compra3), true);
		Assertions.assertEquals(compraService.addCompra(compra4), false);
	}
	
	/**
	 * Test Reporte ventas.
	 */
	@Test
	@Order(6)
	void reporteVentas() {
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
		Compra compra1 = new Compra(cliente1,Date.valueOf("2021-11-22"), listPedido1);
		Compra compra2 = new Compra(cliente2,Date.valueOf("2021-11-21"), listPedido2);
		compraService.addCompra(compra1);
		compraService.addCompra(compra2);
		double dia21 = pedido2.getCantidad()*(pedido2.getProducto().getPrecio());
		double dia22 = pedido1.getCantidad()*(pedido1.getProducto().getPrecio());
		List<Venta> ventas = compraService.generarReporteVentas();
		Assertions.assertEquals(ventas.get(0).getVendidoTotal(), dia21);
		Assertions.assertEquals(ventas.get(1).getVendidoTotal(), dia22);
	}
}
