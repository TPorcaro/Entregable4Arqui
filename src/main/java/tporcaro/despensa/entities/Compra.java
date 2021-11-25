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

import io.swagger.annotations.ApiModelProperty;


/**
 * The Class Compra.
 */
@Entity
public class Compra {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "ID de la compra",name = "id")
	private int id;
	
	/** The cliente. */
	@ManyToOne(cascade=CascadeType.MERGE)
	@ApiModelProperty(notes = "Cliente de la compra",name = "cliente")
	private Cliente cliente;
	
	/** The fecha compra. */
	@ApiModelProperty(notes = "Fecha de la compra",name = "fecha_compra")
	private Date fecha_compra;
	
	/** The precio total. */
	@ApiModelProperty(notes = "Precio total de la compra",name = "precio_total")
	private double precio_total;
	
	/** The pedidos. */
	@ManyToMany(cascade=CascadeType.MERGE)
	@ApiModelProperty(notes = "Pedidos de la compra",name = "pedidos")
	private List<Pedido> pedidos;

	/**
	 * Instantiates a new compra.
	 */
	public Compra() {
		super();
	}

	/**
	 * Instantiates a new compra.
	 *
	 * @param cliente the cliente
	 * @param fecha_compra the fecha compra
	 * @param pedidos the pedidos
	 */
	public Compra(Cliente cliente, Date fecha_compra, List<Pedido> pedidos) {
		super();
		this.cliente = cliente;
		this.fecha_compra = fecha_compra;
		this.pedidos = pedidos;
		this.reCalcularPrecio();
	}
	
	/**
	 * Instantiates a new compra.
	 *
	 * @param cliente the cliente
	 * @param fecha_compra the fecha compra
	 * @param pedidos the pedidos
	 */
	public Compra(Cliente cliente, Long fecha_compra, List<Pedido> pedidos) {
		super();
		System.out.println("con LONG");
		this.cliente = cliente;
		this.fecha_compra = new Date(fecha_compra);
		this.pedidos = pedidos;
		this.reCalcularPrecio();
	}

	/**
	 * Re calcular precio.
	 */
	public void reCalcularPrecio() {
		if(this.pedidos != null) {	
			double precioTotal = 0;
			for (Pedido pedido : pedidos) {
				precioTotal+= (pedido.getProducto().getPrecio() * pedido.getCantidad());
			}
			this.precio_total = precioTotal;
		}
		else
			this.precio_total =  0;
		

	}

	/**
	 * Instantiates a new compra.
	 *
	 * @param id the id
	 * @param cliente the cliente
	 * @param fecha_compra the fecha compra
	 */
	public Compra(int id, Cliente cliente, Date fecha_compra) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.fecha_compra = fecha_compra;
		this.precio_total = (float) 0;
		this.pedidos = new ArrayList<Pedido>();
	}

	/**
	 * Gets the cliente.
	 *
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Sets the cliente.
	 *
	 * @param cliente the new cliente
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * Gets the fecha compra.
	 *
	 * @return the fecha compra
	 */
	public Date getFecha_compra() {
		return fecha_compra;
	}

	/**
	 * Sets the fecha compra.
	 *
	 * @param fecha_compra the new fecha compra
	 */
	public void setFecha_compra(Date fecha_compra) {
		this.fecha_compra = fecha_compra;
	}

	/**
	 * Gets the precio total.
	 *
	 * @return the precio total
	 */
	public double getPrecio_total() {
		return precio_total;
	}

	/**
	 * Sets the precio total.
	 *
	 * @param precio_total the new precio total
	 */
	public void setPrecio_total(double precio_total) {
		this.precio_total = precio_total;
	}

	/**
	 * Gets the pedidos.
	 *
	 * @return the pedidos
	 */
	@JsonIgnore
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	/**
	 * Sets the pedidos.
	 *
	 * @param pedidos the new pedidos
	 */
	@JsonProperty("pedidos")
	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
		this.reCalcularPrecio();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Compra [id=" + id + ", cliente=" + cliente + ", fecha_compra=" + fecha_compra + ", precio_total="
				+ precio_total + ", pedidos=" + pedidos + "]";
	}

	/**
	 * Sets the id.
	 *
	 * @param i the new id
	 */
	public void setId(int i) {
		this.id = i;
	}


	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compra other = (Compra) obj;
		return this.id == other.id;
	}



	

	

	

	
}
