package despensa.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	private Cliente cliente;
	private Timestamp fecha_compra;
	private Float precio_total;
	@OneToMany
	private List<Pedido> pedidos;

	public Compra() {
		super();
	}

	public Compra(Cliente cliente, Timestamp fecha_compra, List<Pedido> pedidos) {
		super();
		this.cliente = cliente;
		this.fecha_compra = fecha_compra;
		this.pedidos = pedidos;
		this.precio_total = this.calcularPrecio();
	}

	private float calcularPrecio() {
		float precioTotal = 0;
		for (Pedido pedido : pedidos) {
			precioTotal+= pedido.getProducto().getPrecio() * pedido.getCantidad();
		}
		return precioTotal;

	}

	public Compra(int id, Cliente cliente, Timestamp fecha_compra) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.fecha_compra = fecha_compra;
		this.precio_total = (float) 0;
		this.pedidos = new ArrayList<Pedido>();
	}

	public Cliente getClient() {
		return cliente;
	}

	public void setClient(Cliente cliente) {
		this.cliente = cliente;
	}

	public Timestamp getFecha_compra() {
		return fecha_compra;
	}

	public void setFecha_compra(Timestamp fecha_compra) {
		this.fecha_compra = fecha_compra;
	}

	public float getPrecio_total() {
		return precio_total;
	}

	public void setPrecio_total(float precio_total) {
		this.precio_total = precio_total;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
		this.precio_total = this.calcularPrecio();
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Compra [id=" + id + ", cliente=" + cliente + ", fecha_compra=" + fecha_compra + ", precio_total="
				+ precio_total + ", pedidos=" + pedidos + "]";
	}

}
