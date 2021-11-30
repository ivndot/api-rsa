package io.ivndot.beans;

public class DecryptBean {

	private int code = 0;
	private String status = null;
	private String description = null;
	private String originalText = null;

	/**
	 * Constructor
	 * 
	 * @param code         Code number [100 = All good; 200 = Error, no content to
	 *                     decrypt; 201 = Error, it could not decrypt the text; 202
	 *                     = Error, no private key sent]
	 * @param status       "ok" or "error"
	 * @param description  Write a little description about it
	 * @param originalText The decrypted text obtained
	 */
	public DecryptBean(int code, String status, String description, String originalText) {
		this.code = code;
		this.status = status;
		this.description = description;
		this.originalText = originalText;
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

	public String getOriginalText() {
		return originalText;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}

}
