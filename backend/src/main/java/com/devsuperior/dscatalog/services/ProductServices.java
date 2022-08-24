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

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exception.DataBaseException;
import com.devsuperior.dscatalog.services.exception.ResourceNotFoundException;

@Service
public class ProductServices {
		
	@Autowired
	private ProductRepository repository; //inj de depencia do ProductRepository, ele é o obj que busca no banco de dados
	
	@Transactional(readOnly = true) //cRud; readyonly para somente leitura e ser mais rapido
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		Page<Product> list = repository.findAll(pageRequest);	
		//resumo em expressao lamba, menos verboso
		return list.map(x -> new ProductDTO(x));
	}

	@Transactional(readOnly = true) //cRud
	public ProductDTO findbyId(Long id) {
		Optional<Product> obj = repository.findById(id); //obj optional que pode ou n ter a categoria la dentro
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!")); //pega entidade no optional e retorna excessao se ocorrer erro
		return new ProductDTO(entity, entity.getCategories()); //retorna nova categoria com a entidade
	}

	@Transactional //Crud
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		//entity.setName(dto.getName());		
		entity = repository.save(entity);
		return new ProductDTO(entity);		
	}

	@Transactional //crUd
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getOne(id); // instancia obj provisorio com as info
			//entity.setName(dto.getName());
			entity = repository.save(entity);
			return new ProductDTO(entity);
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