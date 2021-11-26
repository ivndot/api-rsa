package io.ivndot.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Test {
	
	public static void main(String[] args) {
		File file = new File("/home/ivn/Desktop/private.key");
		String info = readFile(file);
		System.out.println(info);
	}
	
	public static String readFile(File file) {
		/*
		 * try { Scanner myReader = null; String data = ""; myReader = new
		 * Scanner(file);
		 * 
		 * while (myReader.hasNextLine()) { data += myReader.nextLine()+"\n"; //data =
		 * myReader.nextLine(); //System.out.println(data); } myReader.close(); return
		 * data; } catch (FileNotFoundException e) { e.printStackTrace(); return null; }
		 */
        String fileName = file.getAbsolutePath();
        Path filePath = Paths.get(fileName);

        String content=null;
		try {
			content = Files.readString(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

        return content;
	}
	
}
