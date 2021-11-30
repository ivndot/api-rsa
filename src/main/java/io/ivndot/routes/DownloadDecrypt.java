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

@WebServlet("/download-decrypted-text")
public class DownloadDecrypt extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get decrypted text
		String originalText = req.getParameter("originalText");

		if (originalText != null && !originalText.isEmpty()) {
			// there is text to download

			// create file
			File file = FilesUtil.createFile("descifrado.rsa", originalText);

			// send file to client
			FilesUtil.sendFile(resp, "descifrado.rsa", file, "txt");

			// delete file
			if (file != null)
				if (file.exists())
					file.delete();
		} else {
			// ERROR: there is no decrypted text to download

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
