package com.devsuperior.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.services.CategoryServices;

@RestController //Usa recurso do controlador Rest ja implementada, pré-procesamento
@RequestMapping(value = "/categories") //rota do recurso e geralmente no plural
public class CategoryResource { //implementa o recurso do rest, a API controladora em si.
	
	
	@Autowired
	private CategoryServices service;
	
	@GetMapping										  //define o webservice, um endpoint do recurso
	public ResponseEntity<List <Category>> findAll(){ //ResponseEntity é um obj que encapsula uma resposta http
		List<Category> list = service.findall();
		return ResponseEntity.ok().body(list);		   //retorna requisiçao e corpo(e sujeito)
	}
}

//classe web service rest, controladora rest/json, faz requisiçao via http