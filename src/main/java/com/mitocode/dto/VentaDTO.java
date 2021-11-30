package com.mitocode.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class VentaDTO {

	private Integer idVenta;
	@NotNull
	private PersonaDTO persona;

	@NotNull
	private Double importe;

	@NotNull
	private LocalDateTime fecha;
	
	@NotNull
	private List<DetalleVentaDTO> detalleVenta;

	public Integer getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(Integer idVenta) {
		this.idVenta = idVenta;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public List<DetalleVentaDTO> getDetalleVenta() {
		return detalleVenta;
	}

	public void setDetalleVenta(List<DetalleVentaDTO> detalleVenta) {
		this.detalleVenta = detalleVenta;
	}

}
