package com.daol.oms.core.exception;

/**
 * <pre>
 * DaolException
 * </pre>
 * 
 * @since 2018. 1. 16.
 * @author alarm
 */
public class DaolException extends RuntimeException {

	private static final long serialVersionUID = -7834501997247600891L;

	private int errorCode;
	private String errorMsg;

	public DaolException() {
		super();
		this.setErrorCode(-100);
	}

	public DaolException(int errorCode) {
		super();
		this.setErrorCode(errorCode);
	}

	public DaolException(String errorMsg) {
		super(errorMsg);
		this.setErrorCode(-100);
		this.setErrorMsg(errorMsg);
	}

	public DaolException(int errorCode, String errorMsg) {
		super(errorMsg);
		this.setErrorCode(errorCode);
		this.setErrorMsg(errorMsg);
	}

	public DaolException(Throwable cause) {
		super(cause);

		this.setErrorCode(-100);
		this.setErrorMsg(cause.getMessage());
	}

	public DaolException(int errorCode, Throwable cause) {
		super(cause);
		this.setErrorCode(errorCode);
	}

	public DaolException(String errorMsg, Throwable cause) {
		super(errorMsg, cause);
		this.setErrorCode(-100);
		this.setErrorMsg(errorMsg);
	}

	public DaolException(int errorCode, String errorMsg, Throwable cause) {
		super(errorMsg, cause);
		this.setErrorCode(errorCode);
		this.setErrorMsg(errorMsg);
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String _errorMsg) {
		errorMsg = _errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int _errorCode) {
		errorCode = _errorCode;
	}
}
