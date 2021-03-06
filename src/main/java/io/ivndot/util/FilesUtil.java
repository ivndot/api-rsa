package io.ivndot.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class FilesUtil {

	/*
	 **************************************************************************
	 * FUNCTION
	 **************************************************************************/
	/**
	 * Function to create a file, returns the file's created
	 * 
	 * @param fileName Files's name
	 * @param content  Content to be written into the file
	 * @return File
	 */
	public static File createFile(String fileName, String content) {
		try {
			File file = new File(fileName);
			FileWriter writer = new FileWriter(file);
			writer.write(content);
			writer.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 **************************************************************************
	 * FUNCTION
	 **************************************************************************/
	/**
	 * Function to read a file, returns the content of the file
	 * 
	 * @param file The file that will be read
	 * @return String
	 */
	public static String readFile(File file) {

		System.out.println("====================");
		System.out.println("PATH: " + file.getName());
		System.out.println("====================");
		StringBuilder sb = null;

		try {
			Scanner reader = new Scanner(file);
			sb = new StringBuilder();
			while (reader.hasNextLine()) {
				sb.append(reader.nextLine());
				// add line separator if another line exists
				if (reader.hasNextLine())
					sb.append(System.lineSeparator());
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("THERE WAS AN ERROR READING THE CONTENT OF THE FILE");
			return null;
		}

		System.out.println("====================");
		System.out.println(sb.toString());
		System.out.println("====================");

		return sb.toString();

	}

	/*
	 **************************************************************************
	 * FUNCTION
	 **************************************************************************/
	/**
	 * Function to zip multiple files, return the ziped file
	 * 
	 * @param files    List of files
	 * @param fileName File's name
	 * @return File
	 */
	public static File createZipFile(List<File> files, String fileName) {

		File fileZip = new File(fileName);
		FileOutputStream fos = null;
		ZipOutputStream zipOut = null;
		FileInputStream fis = null;

		try {
			fos = new FileOutputStream(fileZip);
			zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
			for (File file : files) {
				fis = new FileInputStream(file);
				ZipEntry ze = new ZipEntry(file.getName());
				System.out.println("Zipping the file: " + file.getName());
				zipOut.putNextEntry(ze);
				byte[] tmp = new byte[4 * 1024];
				int size = 0;
				while ((size = fis.read(tmp)) != -1) {
					zipOut.write(tmp, 0, size);
				}
				zipOut.flush();
				fis.close();
			}
			zipOut.close();
			System.out.println("Done... Zipped the files...");
			return fileZip;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (Exception ex) {

			}
		}
	}

	/*
	 **************************************************************************
	 * FUNCTION
	 **************************************************************************/
	/**
	 * Function to send a file to the client
	 * 
	 * @param resp     HttpServlet response
	 * @param fileName Files's name
	 * @param file     File to be send
	 * @param type     File's type, it could be zip or txt
	 * @throws IOException
	 */
	public static void sendFile(HttpServletResponse resp, String fileName, File file, String type) throws IOException {
		FileInputStream fileInputStream = null;
		OutputStream responseOutputStream = null;

		// modify the header 'content type' depending on file's type
		String contentType = "application/" + (type == "zip" ? "zip" : type == "txt" ? "octet-stream" : "");

		resp.setContentType(contentType);
		resp.addHeader("Content-Disposition", "attachment; filename=" + fileName);
		resp.setContentLength((int) file.length());
		// CORS configuration
		ResponseUtil.setAccessControlHeaders(resp, "GET");

		fileInputStream = new FileInputStream(file);
		responseOutputStream = resp.getOutputStream();
		int bytes;
		while ((bytes = fileInputStream.read()) != -1) {
			responseOutputStream.write(bytes);
		}

		fileInputStream.close();
		responseOutputStream.close();
	}

	/*
	 **************************************************************************
	 * FUNCTION
	 **************************************************************************/
	/**
	 * Function to upload a file to the server, returns the name of the created file
	 * 
	 * @param req            HttpServletRequest
	 * @param servletContext Servlet context
	 * @param parameterName  The name of the parameter
	 * @return File
	 */
	public static File uploadFile(HttpServletRequest req, String servletContext, String parameterName) {
		try {
			// create file and set the path where will be saved
			String uploadPath = servletContext;
			/*
			 * File generatedFile = new File(uploadPath); if (!generatedFile.exists())
			 * generatedFile.mkdir();
			 */

			// write into the file
			Part filePart = req.getPart(parameterName);
			String fileName = filePart.getSubmittedFileName();

			if (fileName != null) {
				// there is a file with name
				for (Part part : req.getParts()) {
					part.write(uploadPath + fileName);
				}
				return new File(uploadPath + fileName);
			} else {
				// there is no file's name
				return null;
			}
		} catch (IOException e) {
			System.out.println(e);
			return null;
		} catch (ServletException se) {
			System.out.println(se);
			return null;
		}
	}

}
