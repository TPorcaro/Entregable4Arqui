package tporcaro.despensa.DTO;

import java.util.Date;

public class Venta {

	private Date fecha;
	private double vendidoTotal;
	
	public Venta() {
		super();
	}

	public Venta(Date fecha, double vendidoTotal) {
		super();
		this.fecha = fecha;
		this.vendidoTotal = vendidoTotal;

	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getVendidoTotal() {
		return vendidoTotal;
	}

	public void setVendidoTotal(double vendidoTotal) {
		this.vendidoTotal = vendidoTotal;
	}

//	public int getCantidadVentas() {
//		return cantidadVentas;
//	}
//
//	public void setCantidadVentas(int cantidadVentas) {
//		this.cantidadVentas = cantidadVentas;
//	}

	@Override
	public String toString() {
		return "Venta [fecha=" + fecha + ", vendidoTotal=" + vendidoTotal +  "]";
	}
	
	
}
