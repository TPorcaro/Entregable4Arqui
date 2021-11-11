package tporcaro.despensa.entities;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(cascade=CascadeType.MERGE)
	private Cliente cliente;
	private Date fecha_compra;
	private Float precio_total;
	@ManyToMany(cascade=CascadeType.MERGE)
	private List<Pedido> pedidos;

	public Compra() {
		super();
	}

	public Compra(Cliente cliente, Date fecha_compra, List<Pedido> pedidos) {
		super();
		this.cliente = cliente;
		this.fecha_compra = fecha_compra;
		this.pedidos = pedidos;
		this.reCalcularPrecio();
	}
	public Compra(Cliente cliente, Long fecha_compra, List<Pedido> pedidos) {
		super();
		System.out.println("con LONG");
		this.cliente = cliente;
		this.fecha_compra = new Date(fecha_compra);
		this.pedidos = pedidos;
		this.reCalcularPrecio();
	}

	public void reCalcularPrecio() {
		if(this.pedidos != null) {	
			float precioTotal = 0;
			for (Pedido pedido : pedidos) {
				precioTotal+= (pedido.getProducto().getPrecio() * pedido.getCantidad());
			}
			this.precio_total = precioTotal;
		}
		else
			this.precio_total = (float) 0;
		

	}

	public Compra(int id, Cliente cliente, Date fecha_compra) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.fecha_compra = fecha_compra;
		this.precio_total = (float) 0;
		this.pedidos = new ArrayList<Pedido>();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getFecha_compra() {
		return fecha_compra;
	}

	public void setFecha_compra(Date fecha_compra) {
		this.fecha_compra = fecha_compra;
	}

	public float getPrecio_total() {
		return precio_total;
	}

	public void setPrecio_total(float precio_total) {
		this.precio_total = precio_total;
	}

	@JsonIgnore
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	@JsonProperty("pedidos")
	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
		this.reCalcularPrecio();
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
