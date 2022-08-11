package com.devsuperior.dscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.entities.Category;

@RestController //Usa recurso do controlador Rest ja implementada, pré-procesamento
@RequestMapping(value = "/categories")
public class CategoryResource { //implementa o recurso do rest, a API controladora em si.
	
	@GetMapping										  //define o webservice, um endpoint do recurso
	public ResponseEntity<List <Category>> findAll(){ //ResponseEntity é um obj que encapsula uma resposta http
		List<Category> list = new ArrayList<>();
		list.add(new Category(1L, "Books"));
		list.add(new Category(2L, "Eletronics"));
		return ResponseEntity.ok().body(list);		   //retorna requisiçao e corpo(e sujeito)
	}
}
