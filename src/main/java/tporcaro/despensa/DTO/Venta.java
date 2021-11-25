package tporcaro.despensa.DTO;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class Venta.
 */
public class Venta {

	/** The fecha. */
	@ApiModelProperty(notes = "Fecha",name = "fecha")
	private Date fecha;
	
	/** The vendido total. */
	@ApiModelProperty(notes = "Cantidad total vendida",name = "vendidoTotal")
	private double vendidoTotal;
	
	/**
	 * Instantiates a new venta.
	 */
	public Venta() {
		super();
	}

	/**
	 * Instantiates a new venta.
	 *
	 * @param fecha the fecha
	 * @param vendidoTotal the vendido total
	 */
	public Venta(Date fecha, double vendidoTotal) {
		super();
		this.fecha = fecha;
		this.vendidoTotal = vendidoTotal;

	}

	/**
	 * Gets the fecha.
	 *
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Sets the fecha.
	 *
	 * @param fecha the new fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Gets the vendido total.
	 *
	 * @return the vendido total
	 */
	public double getVendidoTotal() {
		return vendidoTotal;
	}

	/**
	 * Sets the vendido total.
	 *
	 * @param vendidoTotal the new vendido total
	 */
	public void setVendidoTotal(double vendidoTotal) {
		this.vendidoTotal = vendidoTotal;
	}
	/**
 * To string.
 *
 * @return the string
 */
@Override
	public String toString() {
		return "Venta [fecha=" + fecha + ", vendidoTotal=" + vendidoTotal +  "]";
	}
	
	
}
