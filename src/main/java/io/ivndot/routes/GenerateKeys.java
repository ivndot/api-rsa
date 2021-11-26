package io.ivndot.routes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Base64;

import io.ivndot.util.FilesUtil;
import io.ivndot.util.RSAKeysUtil;

@WebServlet("/generate-keys")
public class GenerateKeys extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// generate pair of keys
		RSAKeysUtil keys = new RSAKeysUtil();

		// file's names
		String publicKeyName = "public.key";
		String privateKeyName = "private.key";

		// create files
		File publicKeyFile = FilesUtil.createFile(publicKeyName,
				Base64.getEncoder().encodeToString(keys.getPublicKey().getEncoded()));
		File privateKeyFile = FilesUtil.createFile(privateKeyName,
				Base64.getEncoder().encodeToString(keys.getPrivateKey().getEncoded()));

		// create list of files
		List<File> filesList = new ArrayList<File>();
		filesList.add(publicKeyFile);
		filesList.add(privateKeyFile);

		if (publicKeyFile != null && privateKeyFile != null) {
			// files were correctly generated and written
			try {
				// zip file's name
				String fileZipName = "keys.zip";
				// create the zip file
				File zip = FilesUtil.createZipFile(filesList, fileZipName);
				// send to the client
				FilesUtil.sendFile(resp, fileZipName, zip, "zip");
				// delete file
				zip.delete();

			} catch (Exception ex) {
				System.out.println("Error sending file");
				ex.printStackTrace();
			}

		} else {
			// there was an error creating or writing into the files
			System.out.println("Null file");
		}

		// delete files
		privateKeyFile.delete();
		publicKeyFile.delete();
	}

}
