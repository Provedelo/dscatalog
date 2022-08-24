package com.devsuperior.dscatalog.services.exception;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String msg) {
		super(msg); //super passa o argumento para o construtor da super classe
	}

}