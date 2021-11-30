package io.ivndot.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		File file = new File("/home/ivn/Desktop/test.txt");
		String info = readFile(file);
		System.out.println("====================");
		System.out.println(info);
		System.out.println("====================");

	}

	private static String readFile(File file) {

		StringBuilder sb = new StringBuilder();

		try {
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()) {
				sb.append(reader.nextLine());
				// add line separator if another line exists
				if (reader.hasNextLine())
					sb.append(System.lineSeparator());
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
	/*
	 * public static String readFile(File file) {
	 * 
	 * String fileName = file.getAbsolutePath(); Path filePath =
	 * Paths.get(fileName);
	 * 
	 * String content=null; try { content = Files.readString(filePath); } catch
	 * (IOException e) { e.printStackTrace(); }
	 * 
	 * return content; }
	 */

}
