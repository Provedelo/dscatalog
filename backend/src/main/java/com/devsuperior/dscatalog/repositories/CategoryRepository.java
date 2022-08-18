package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	
}
//@Repository se for repositorio
//para todo banco de dados relacional
//precisa ser implementado pela camada de serviço, neste caso CategoryService
//camada de acesso a dados, faz o CRUD no banco de dados, sendo generico e esperando 2 parametros, <identidade, id>