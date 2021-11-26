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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

public class FilesUtil {

	/*
	 **************************************************************************
	 * FUNCTION
	 **************************************************************************/
	/**
	 * 
	 * @param fileName Files's name
	 * @param key      Public or private key encoded
	 * @return File
	 */
	public static File createFile(String fileName, byte[] key) {
		try {
			File file = new File(fileName);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(key);
			fos.flush();
			fos.close();
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
	public static String readFile(String path) {
		return "";
	}

	/*
	 **************************************************************************
	 * FUNCTION
	 **************************************************************************/
	/**
	 * Function to zip multiple files
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
	 * Send file to the client
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
		String contentType = "application/" + (type == "zip" ? "zip" : "octet-stream");

		resp.setContentType(contentType);
		resp.addHeader("Content-Disposition", "attachment; filename=" + fileName);
		resp.setContentLength((int) file.length());
		// CORS configuration
		HeadersUtil.setAccessControlHeaders(resp, "GET");

		fileInputStream = new FileInputStream(file);
		responseOutputStream = resp.getOutputStream();
		int bytes;
		while ((bytes = fileInputStream.read()) != -1) {
			responseOutputStream.write(bytes);
		}

		fileInputStream.close();
		responseOutputStream.close();
	}

}
