package com.springcloud.gateway.models;

public class APIWSResponse<R> {
	
	private int responseStatus;
	private R responseBody;
	/**
	 * @return the responseStatus
	 */
	public int getResponseStatus() {
		return responseStatus;
	}
	/**
	 * @param responseStatus the responseStatus to set
	 */
	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}
	/**
	 * @return the responseBody
	 */
	public R getResponseBody() {
		return responseBody;
	}
	/**
	 * @param responseBody the responseBody to set
	 */
	public void setResponseBody(R responseBody) {
		this.responseBody = responseBody;
	}

}
