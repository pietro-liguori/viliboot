package com.vili.demo.exception;

import java.io.Serial;

public class InternalErrorException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;
	
	public InternalErrorException() {
		super("Uncaught error");
	}
}