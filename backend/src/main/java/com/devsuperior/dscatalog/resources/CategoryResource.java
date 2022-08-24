package com.devsuperior.dscatalog.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	
	@PostMapping //padrao rest, inserindo usando post
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto){ //como é um obj com um ou mais atributos a ser inserido, sendo o proprio obj classe 
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri(); //retorna o codigo 201 e o endereço do recurso criado
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}") //atualizando usando put 
	public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto){  
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}") //deletando usando delete 
	public ResponseEntity<Void> delete(@PathVariable Long id){  //corpo pode ser vazio, n tem resposta porque foi deletado
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}

//classe web service rest, controladora rest/json, faz requisiçao via http
//cod http 200 requicicao com sucesso, 201 recurso criado, 204 deu certo e corpo vazio da resposta