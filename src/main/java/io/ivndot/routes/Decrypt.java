package io.ivndot.routes;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.ivndot.beans.DecryptBean;
import io.ivndot.util.FilesUtil;
import io.ivndot.util.RSAUtil;
import io.ivndot.util.ResponseUtil;

@WebServlet("/decrypt")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 10, // 10 MB
		maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class Decrypt extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// get parameters
		String textToDecrypt = req.getParameter("textToDecrypt");
		String privateKey = req.getParameter("privateKey");
		File fileToDecrypt = FilesUtil.uploadFile(req, getServletContext().getRealPath(""), "fileToDecrypt");

		// java bean to send the response
		DecryptBean decryptBean = null;

		if (privateKey != null && !privateKey.equals("")) {
			// the private key is not empty

			// content that will be decrypted
			String content = null;
			// decrypted content
			String originalContent = null;

			if (fileToDecrypt != null) {
				// the file to decrypt was upload successfully
				// decrypt file content

				// get the content of the file
				content = FilesUtil.readFile(fileToDecrypt);

			} else if (textToDecrypt != null && !textToDecrypt.equals("")) {
				// decrypt text

				// get the sent text encoded in base64
				content = new String(Base64.getDecoder().decode(textToDecrypt));

			} else {
				// ERROR: there is no content to decrypt
				decryptBean = new DecryptBean(200, "error", "There is no content to decrypt", "");

				// send response
				ResponseUtil.sendJSONResponse(resp, decryptBean, "POST");

				System.out.println("ERROR: there is no content to encrypt");
			}

			try {
				// decrypt the content
				originalContent = RSAUtil.decrypt(content, privateKey);

				// bean
				decryptBean = new DecryptBean(100, "ok", "Correctly decrypted content", originalContent);

			} catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException
					| NoSuchAlgorithmException e) {
				// ERROR: there was an error decrypting the content
				e.printStackTrace();

				// bean
				decryptBean = new DecryptBean(201, "error", "There was an error decrypting the content", "");
			}

		} else {
			// ERROR: there was an error with the private key

			// bean
			decryptBean = new DecryptBean(202, "error", "No private key sent", "");
		}

		// delete file
		if (fileToDecrypt != null)
			if (fileToDecrypt.exists())
				fileToDecrypt.delete();

		// send response
		ResponseUtil.sendJSONResponse(resp, decryptBean, "POST");
	}

}
