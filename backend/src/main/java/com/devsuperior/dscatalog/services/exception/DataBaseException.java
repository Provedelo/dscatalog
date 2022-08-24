package com.devsuperior.dscatalog.services.exception;

public class DataBaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DataBaseException(String msg) {
		super(msg); //super passa o argumento para o construtor da super classe
	}

}