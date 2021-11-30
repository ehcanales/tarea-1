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

import com.mitocode.dto.PersonaDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Persona;
import com.mitocode.service.IPersonaService;

@RestController
@RequestMapping("/personas")
public class PersonaController {
	
	@Autowired
	private IPersonaService service;
	
	@Autowired
	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<List<PersonaDTO>> listar() throws Exception{
		List<PersonaDTO> lista = service.listar().stream().map(p -> mapper.map(p, PersonaDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PersonaDTO> listarPorId(@PathVariable("id") Integer id) throws Exception{
		PersonaDTO dtoResponse;
		Persona obj = service.listarPorId(id);
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}else {
			dtoResponse = mapper.map(obj, PersonaDTO.class);
		}
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PersonaDTO> registrar(@Valid @RequestBody PersonaDTO dtoRequest) throws Exception {
		Persona p = mapper.map(dtoRequest, Persona.class);
		Persona obj = service.registrar(p);
		PersonaDTO dtoResponse = mapper.map(obj, PersonaDTO.class);
		return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
	}
	
	
	@PutMapping
	public ResponseEntity<PersonaDTO> modificar(@Valid @RequestBody PersonaDTO dtoRequest) throws Exception {
		Persona per = service.listarPorId(dtoRequest.getIdPersona());
		
		if(per == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + dtoRequest.getIdPersona());	
		}		
		
		Persona p = mapper.map(dtoRequest, Persona.class);		 
		Persona obj = service.modificar(p);		
		PersonaDTO dtoResponse = mapper.map(obj, PersonaDTO.class);
		
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Persona per = service.listarPorId(id);
		
		if(per == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
