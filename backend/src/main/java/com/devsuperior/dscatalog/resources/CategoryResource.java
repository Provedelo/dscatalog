package com.devsuperior.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryServices;

@RestController //Usa recurso do controlador Rest ja implementada, pré-procesamento
@RequestMapping(value = "/categories") //rota do recurso e geralmente no plural
public class CategoryResource { //implementa o recurso do rest, a API controladora em si.
	
	
	@Autowired
	private CategoryServices service;
	
	@GetMapping										  //define o webservice, um endpoint do recurso
	public ResponseEntity<List <CategoryDTO>> findAll(){ //ResponseEntity é um obj que encapsula uma resposta http
		List<CategoryDTO> list = service.findall();
		return ResponseEntity.ok().body(list);		   //retorna requisiçao e corpo(e sujeito)
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
		CategoryDTO dto = service.findbyId(id);
		return ResponseEntity.ok().body(dto);
	}
}

//classe web service rest, controladora rest/json, faz requisiçao via http