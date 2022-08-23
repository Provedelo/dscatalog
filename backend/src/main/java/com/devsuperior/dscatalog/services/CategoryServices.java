package com.devsuperior.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryServices {
		
	@Autowired
	private CategoryRepository repository; //inj de depencia do CategoryRepository
	
	@Transactional(readOnly = true) //readyonly para somente leitura e ser mais rapido
	public List<Category> findall(){
		return repository.findAll();
	}
	
}
//camada de serviço, onde implementa a camada de dados(resource)
//@Service da camada de serviço, mostra a classe como componente de injecao de dependencia, spring gerencia de forma automatizada
//@Component para genericos sem significado direto, para injeção de dependencia
//@Autowired, faz injecao de dependencia automatica, instancia controlada pelo spring
//@Transactional, garante a transacao no banco de dados,  orquestrado pela spring, faz tudo ou nada na propriedade ACID