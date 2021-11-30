package io.ivndot.routes;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import io.ivndot.util.FilesUtil;
import io.ivndot.util.ResponseUtil;

@WebServlet("/download-encrypted-text")
public class DownloadEncrypt extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// get encrypted text
		String encryptedText = req.getParameter("encryptedText");

		if (encryptedText != null && !encryptedText.isEmpty()) {
			// there is text to download

			// create file
			File file = FilesUtil.createFile("cifrado.rsa", encryptedText);

			// send file to client
			FilesUtil.sendFile(resp, "cifrado.rsa", file, "txt");

			// delete file
			if (file.exists())
				file.delete();
		} else {
			// ERROR: there is no encrypted text to download

			// JSON object
			JSONObject obj = new JSONObject();
			obj.put("status", "error");
			obj.put("description", "There is no text to download");

			// content type
			resp.setContentType("application/json");
			// CORS configuration
			ResponseUtil.setAccessControlHeaders(resp, "POST");
			// send response
			resp.getOutputStream().println(obj.toString());
		}

	}

}
