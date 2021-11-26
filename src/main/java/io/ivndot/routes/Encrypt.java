package io.ivndot.routes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONObject;

import io.ivndot.util.HeadersUtil;

@WebServlet("/encrypt")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 10, // 10 MB
		maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class Encrypt extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/**
		 * { status: " ", description: " " textEncrypted: "" }
		 */
		// get parameters
		String textToEncrypt = req.getParameter("textToEncrypt");
		String fileToEncrypt = req.getParameter("fileToEncrypt");
		//String publicKeyFile = req.getParameter("publicKey");

		// json object to send result
		JSONObject json = new JSONObject();

		// if (publicKeyFile != null) {
		// the public key file was send
		if (fileToEncrypt != null) {
			// encrypt the content of a file
			System.out.println("SI hay archivo");
			// get the file to read its content
			File file = uploadFile(req, "fileToEncrypt");

			if (file != null) {
				// the file was upload succesfully

				// read the content of the file
				String content = readFile(file);

				System.out.println("Contenido: " + content);

			} else {
				// the file could not upload
				System.out.println("the file could not upload");
			}

		} else if (textToEncrypt != null) {
			// encrypt the send text
			System.out.println("no hay archivo");
		}
		// }

		// send response
		resp.setContentType("application/json");
		resp.getOutputStream().println(json.toString());
		// CORS configuration
		HeadersUtil.setAccessControlHeaders(resp, "POST");
	}

	public static String readFile(File file) {
		String fileName = file.getAbsolutePath();
		Path filePath = Paths.get(fileName);

		String content = null;
		try {
			content = Files.readString(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

	private File uploadFile(HttpServletRequest req, String parameterName) {
		try {
			// create file and set the path where will be saved
			String uploadPath = getServletContext().getRealPath("");
			File generatedFile = new File(uploadPath);
			if (!generatedFile.exists())
				generatedFile.mkdir();

			// write into the file
			Part filePart = req.getPart(parameterName);
			String fileName = filePart.getSubmittedFileName();
			for (Part part : req.getParts()) {
				part.write(uploadPath + File.separator + fileName);
			}
			return generatedFile;
		} catch (IOException e) {
			System.out.println(e);
			return null;
		} catch (ServletException se) {
			System.out.println(se);
			return null;
		}
	}
}
