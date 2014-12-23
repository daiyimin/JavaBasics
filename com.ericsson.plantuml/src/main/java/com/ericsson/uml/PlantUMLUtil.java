package com.ericsson.uml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class PlantUMLUtil {
	/**
	 * 
	 * @param source  Source UML string
	 * @param png   PNG file output stream
	 * @throws IOException
	 */
	public static void transfromStringToUML(String source, OutputStream png) throws IOException {
		SourceStringReader reader = new SourceStringReader(source);
		// Write the first image to "png"
		String desc = reader.generateImage(png);
		png.close();
	}
	
	/**
	 * 
	 * @param source  Source UML string
	 * @param png   PNG file name
	 * @throws IOException
	 */
	public static void transfromStringToUML(String source, String png) throws IOException {
		File f = new File(png);
		if (f.exists()) {
			f.delete();
		}
		f = new File(png);
		OutputStream pngStream = new FileOutputStream(f, true);
		transfromStringToUML(source, pngStream);
	}
	
	
	public static void main(String[] args) throws IOException {
		OutputStream png = null;
		String source = "@startuml\n";
		source += "Bob -> Alice : [[file:///C:/Ericsson/test.html]] hello\n";
		source += "Alice -> Bob : [[file:///C:/Ericsson/test.html]] hi\n";
		source += "@enduml\n";
		
		
		File f = new File("c:\\Ericsson\\test.svg");
		OutputStream svg = new FileOutputStream(f, true);
		 SourceStringReader reader = new SourceStringReader(source);
//		 final ByteArrayOutputStream os = new ByteArrayOutputStream();
		 // Write the first image to "os"
		 String desc = reader.generateImage(svg, new FileFormatOption(FileFormat.SVG));
		 svg.close();

		 // The XML is stored into svg
//		 final String svg = new String(os.toByteArray());
	}

}
