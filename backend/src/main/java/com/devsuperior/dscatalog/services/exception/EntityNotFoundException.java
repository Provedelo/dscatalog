package com.devsuperior.dscatalog.services.exception;

public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EntityNotFoundException(String msg) {
		super(msg); //super passa o argumento para o construtor da super classe
	}

}