package com.mitocode.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mitocode.dto.VentaDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Venta;
import com.mitocode.service.IVentaService;

@RestController
@RequestMapping("/ventas")
public class VentaController {
	
	@Autowired
	private IVentaService service;
	
	@Autowired
	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<List<VentaDTO>> listar() throws Exception{
		List<VentaDTO> lista = service.listar().stream().map(p -> mapper.map(p, VentaDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<VentaDTO> listarPorId(@PathVariable("id") Integer id) throws Exception{
		VentaDTO dtoResponse;
		Venta obj = service.listarPorId(id);
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}else {
			dtoResponse = mapper.map(obj, VentaDTO.class);
		}
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<VentaDTO> registrar(@Valid @RequestBody VentaDTO dtoRequest) throws Exception {
		Venta p = mapper.map(dtoRequest, Venta.class);
		Venta obj = service.registrarTransaccional(p, null);
		VentaDTO dtoResponse = mapper.map(obj, VentaDTO.class);
		return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
	}

	
	@PutMapping
	public ResponseEntity<VentaDTO> modificar(@Valid @RequestBody VentaDTO dtoRequest) throws Exception {
		Venta ven = service.listarPorId(dtoRequest.getIdVenta());
		
		if(ven == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + dtoRequest.getIdVenta());	
		}		
		
		Venta p = mapper.map(dtoRequest, Venta.class);		 
		Venta obj = service.modificar(p);		
		VentaDTO dtoResponse = mapper.map(obj, VentaDTO.class);
		
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Venta ven = service.listarPorId(id);
		
		if(ven == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	
	
}
