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

import com.mitocode.dto.ProductoDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Producto;
import com.mitocode.service.IProductoService;

@RestController
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	private IProductoService service;
	
	@Autowired
	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<List<ProductoDTO>> listar() throws Exception{
		List<ProductoDTO> lista = service.listar().stream().map(p -> mapper.map(p, ProductoDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductoDTO> listarPorId(@PathVariable("id") Integer id) throws Exception{
		ProductoDTO dtoResponse;
		Producto obj = service.listarPorId(id);
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}else {
			dtoResponse = mapper.map(obj, ProductoDTO.class);
		}
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ProductoDTO> registrar(@Valid @RequestBody ProductoDTO dtoRequest) throws Exception {
		Producto p = mapper.map(dtoRequest, Producto.class);
		Producto obj = service.registrar(p);
		ProductoDTO dtoResponse = mapper.map(obj, ProductoDTO.class);
		return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
	}
	
	
	@PutMapping
	public ResponseEntity<ProductoDTO> modificar(@Valid @RequestBody ProductoDTO dtoRequest) throws Exception {
		Producto pro = service.listarPorId(dtoRequest.getIdProducto());
		
		if(pro == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + dtoRequest.getIdProducto());	
		}		
		
		Producto p = mapper.map(dtoRequest, Producto.class);		 
		Producto obj = service.modificar(p);		
		ProductoDTO dtoResponse = mapper.map(obj, ProductoDTO.class);
		
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Producto pro = service.listarPorId(id);
		
		if(pro == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
