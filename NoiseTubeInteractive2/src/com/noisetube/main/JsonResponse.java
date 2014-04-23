package com.noisetube.main;

/**
 * Model representing JSON needed to parse a result.
 * Contains status for response type, Message for response body
 * @author Tim
 */

public class JsonResponse {
	private String status;
	private String message;
	
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String ERROR_NOTJSON = "body is not in Json format";
	
	public boolean hasErrors() {
		return status.equalsIgnoreCase(ERROR);
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "JsonResponse [status=" + status + ", message=" + message + "]";
	}
}
