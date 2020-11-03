package com.kpst.demo.exception;

public class ServiceUnavailableException extends Exception {

	public ServiceUnavailableException() {
		super();
	}

	public ServiceUnavailableException(String message) {
		super(message);
	}

	public ServiceUnavailableException(Exception cause) {
		super(cause);
	}

	public ServiceUnavailableException(String message, Exception cause) {
		super(message, cause);
	}
}
