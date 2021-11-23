package tporcaro.despensa.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import io.swagger.annotations.ApiModelProperty;

@Entity
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "Id del pedido",name = "id")
	private int id;
	@ManyToOne
	@ApiModelProperty(notes = "Producto que tiene el pedido",name = "producto")
	private Producto producto;
	@ApiModelProperty(notes = "Cantidad que tiene el pedido",name = "cantidad")
	private int cantidad;

	public Pedido() {
		super();
	}

	public Pedido(Producto producto, int cantidad) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", producto=" + producto + ", cantidad=" + cantidad + "]";
	}

	public void setId(int i) {
		this.id = i;	
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return cantidad == other.cantidad && id == other.id && Objects.equals(producto, other.producto);
	}
	

}
