package io.ivndot.routes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import io.ivndot.util.FilesUtil;

@WebServlet("/test")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 10, // 10 MB
		maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class TestNode extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		

		// java bean to send the response
		//EncryptBean encryptBean = null;
		
		String publicKey = req.getParameter("publicKey");
		String fileToEncrypt = FilesUtil.uploadFile(req,getServletContext().getRealPath(""),"fileToEncrypt");
		
		String content = FilesUtil.readFile(fileToEncrypt);
		
		System.out.println("====PUBLIC KEY====");
		System.out.println(publicKey);
		System.out.println("====PUBLIC KEY====");
		
		System.out.println("====FILE CONTENT====");
		System.out.println(content);
		System.out.println("====FILE CONTENT====");
	}
	
	

}
