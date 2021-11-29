package io.ivndot.beans;

public class EncryptBean {

	private String status = null;
	private String description = null;
	private String encryptedText = null;

	/**
	 * Constructor of Encrypt Bean
	 * 
	 * @param status        "ok" or "error"
	 * @param description   Write a little description about it
	 * @param encryptedText The encrypted text generated
	 */
	public EncryptBean(String status, String description, String encryptedText) {
		super();
		this.status = status;
		this.description = description;
		this.encryptedText = encryptedText;
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
