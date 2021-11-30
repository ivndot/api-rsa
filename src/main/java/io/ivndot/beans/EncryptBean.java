package io.ivndot.beans;

public class EncryptBean {

	private int code = 0;
	private String status = null;
	private String description = null;
	private String encryptedText = null;

	/**
	 * Constructor of Encrypt Bean
	 * 
	 * @param code          Code number [100 = All good; 200 = Error, no content to
	 *                      encrypt; 201 = Error, it could not encrypt the text; 202
	 *                      = Error, no public key sent]
	 * @param status        "ok" or "error"
	 * @param description   Write a little description about it
	 * @param encryptedText The encrypted text generated
	 */
	public EncryptBean(int code, String status, String description, String encryptedText) {
		this.code = code;
		this.status = status;
		this.description = description;
		this.encryptedText = encryptedText;
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

	public String getEncryptedText() {
		return encryptedText;
	}

	public void setEncryptedText(String encryptedText) {
		this.encryptedText = encryptedText;
	}

}
