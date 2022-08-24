package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exception.ResourceNotFoundException;

@Service
public class CategoryServices {
		
	@Autowired
	private CategoryRepository repository; //inj de depencia do CategoryRepository, ele é o obj que busca no banco de dados
	
	@Transactional(readOnly = true) //cRud; readyonly para somente leitura e ser mais rapido
	public List<CategoryDTO> findall(){
		List<Category> list = repository.findAll();	
		//resumo em expressao lamba, menos verboso
		return list.stream().map(x -> new CategoryDTO(x))
		.collect(Collectors.toList());
	}

	@Transactional(readOnly = true) //cRud
	public CategoryDTO findbyId(Long id) {
		Optional<Category> obj = repository.findById(id); //obj optional que pode ou n ter a categoria la dentro
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!")); //pega entidade no optional e retorna excessao se ocorrer erro
		return new CategoryDTO(entity); //retorna nova categoria com a entidade
	}

	@Transactional //Crud
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);		
	}

	@Transactional //crUd
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne(id); // instancia obj provisorio com as info
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CategoryDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new  ResourceNotFoundException("Id not found" + id);
		}
	}
	
}
//camada de serviço, onde implementa a camada de dados(resource)
//@Service da camada de serviço, mostra a classe como componente de injecao de dependencia, spring gerencia de forma automatizada
//@Component para genericos sem significado direto, para injeção de dependencia
//@Autowired, faz injecao de dependencia automatica, instancia controlada pelo spring
//@Transactional, garante a transacao no banco de dados,  orquestrado pela spring, faz tudo ou nada na propriedade ACID

/*
 * public List<CategoryDTO> findall(){
 *
 *List<Category> list = repository.findAll();
 *List<CategoryDTO> listDTO = new ArrayList<>();
 * 
 *for(Category obj : list) {
 *	listDTO.add(new CategoryDTO(obj));
 *}
 *return listDTO;
 * //metodo verboso para implementação de varredura de obj de classe para classe dto
 */