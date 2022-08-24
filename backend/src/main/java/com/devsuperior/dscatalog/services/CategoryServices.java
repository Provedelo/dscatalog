package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exception.DataBaseException;
import com.devsuperior.dscatalog.services.exception.ResourceNotFoundException;

@Service
public class CategoryServices {
		
	@Autowired
	private CategoryRepository repository; //inj de depencia do CategoryRepository, ele é o obj que busca no banco de dados
	
	@Transactional(readOnly = true) //cRud; readyonly para somente leitura e ser mais rapido
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
		Page<Category> list = repository.findAll(pageRequest);	
		//resumo em expressao lamba, menos verboso
		return list.map(x -> new CategoryDTO(x));
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
			throw new  ResourceNotFoundException("Id not found " + id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id do not exist " + id);
		}
	}

	//não usa transactional no delete
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("id not found " + id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException("Integrit violation ");
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