package io.ivndot.beans;

public class LoginBean {

	private int code = 0;
	private String status = null;
	private String description = null;

	/**
	 * Constructor of LoginBean
	 * 
	 * @param code        Code number [100 = All good; 200 = Error, user or password
	 *                    is wrong; 201 = Error, user and password are empty]
	 * @param status      "ok" or "error"
	 * @param description A little description about the response
	 */
	public LoginBean(int code, String status, String description) {
		this.code = code;
		this.status = status;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
