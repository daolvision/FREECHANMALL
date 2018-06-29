package com.daol.oms.core.exception;

/**
 * <pre>
 * PermissionException
 * </pre>
 * 
 * @since 2018. 1. 16.
 * @author alarm
 */
public class PermissionException extends RuntimeException {

	private static final long serialVersionUID = -3236205979151742805L;

	public PermissionException() {
		super();
	}

	public PermissionException(String errorMsg) {
		super(errorMsg);
	}

	public PermissionException(Throwable cause) {
		super(cause);
	}
}
