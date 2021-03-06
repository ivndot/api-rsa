package io.ivndot.routes;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.ivndot.beans.EncryptBean;
import io.ivndot.util.FilesUtil;
import io.ivndot.util.ResponseUtil;
import io.ivndot.util.RSAUtil;

@WebServlet("/encrypt")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 10, // 10 MB
		maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class Encrypt extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// get parameters
		String textToEncrypt = req.getParameter("textToEncrypt");
		String publicKey = req.getParameter("publicKey");
		File fileToEncrypt = FilesUtil.uploadFile(req, getServletContext().getRealPath(""), "fileToEncrypt");
		System.out.println("TEXT:" + textToEncrypt);
		// java bean to send the response
		EncryptBean encryptBean = null;

		if (publicKey != null && !publicKey.equals("")) {
			// the public key file was upload successfully

			// content that will be encrypted
			String content = null;
			// encrypted content
			String encryptedContent = null;

			if (fileToEncrypt != null) {
				// the file to encrypt was upload successfully
				// encrypt file content

				// get the content of the file
				content = FilesUtil.readFile(fileToEncrypt);

			} else if (textToEncrypt != null && !textToEncrypt.equals("")) {
				// encrypt text

				// get the sent text encoded in base64
				content = textToEncrypt;

			} else {
				// ERROR: there is no content to encrypt
				encryptBean = new EncryptBean(200, "error", "There is no content to encrypt", "");

				// send response
				ResponseUtil.sendJSONResponse(resp, encryptBean, "POST");

				System.out.println("ERROR: there is no content to encrypt");
			}

			try {
				// encrypt the content
				encryptedContent = RSAUtil.encrypt(content, publicKey);

				// bean
				encryptBean = new EncryptBean(100, "ok", "Correctly encrypted content", encryptedContent);

			} catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException
					| NoSuchAlgorithmException e) {
				// ERROR: there was an error encrypting the content
				e.printStackTrace();

				// bean
				encryptBean = new EncryptBean(201, "error", "There was an error encrypting the content", "");
			}

		} else {
			// ERROR: there was an error with the public key

			// bean
			encryptBean = new EncryptBean(202, "error", "No public key sent", "");
		}

		// delete file
		if (fileToEncrypt != null)
			if (fileToEncrypt.exists())
				fileToEncrypt.delete();

		// send response
		ResponseUtil.sendJSONResponse(resp, encryptBean, "POST");

	}

}
