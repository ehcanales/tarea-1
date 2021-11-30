package com.mitocode.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

public class PersonaDTO {

	private Integer idPersona;
	
	@NotEmpty
	@Size(min = 3, message = "{nombres.size}")
	private String nombres;
	
	@NotNull
	@Size(min = 3, message = "{apellidos.size}")
	private String apellidos;
	
	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

}
