package despensa.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToMany(mappedBy = "compras")
	private Cliente client;
	private Timestamp fecha_compra;
	private float precio_total;
	@OneToMany
	private List<Pedido> pedidos;

	public Compra() {
		super();
	}

	public Compra(Cliente client, Timestamp fecha_compra, float precio_total, List<Pedido> pedidos) {
		super();
		this.client = client;
		this.fecha_compra = fecha_compra;
		this.precio_total = precio_total;
		this.pedidos = pedidos;
	}

	public Compra(int id, Cliente client, Timestamp fecha_compra, float precio_total) {
		super();
		this.id = id;
		this.client = client;
		this.fecha_compra = fecha_compra;
		this.precio_total = precio_total;
		this.pedidos = new ArrayList<Pedido>();
	}

	public Cliente getClient() {
		return client;
	}

	public void setClient(Cliente client) {
		this.client = client;
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
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Compra [id=" + id + ", client=" + client + ", fecha_compra=" + fecha_compra + ", precio_total="
				+ precio_total + ", pedidos=" + pedidos + "]";
	}

}
