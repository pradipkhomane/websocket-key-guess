package com.socket.keygame.exception;

public class KeyGameException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public KeyGameException() {
		super();
	}
	public KeyGameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public KeyGameException(String message, Throwable cause) {
		super(message, cause);
	}
	public KeyGameException(String message) {
		super(message);
	}
	public KeyGameException(Throwable cause) {
		super(cause);
	}
}
