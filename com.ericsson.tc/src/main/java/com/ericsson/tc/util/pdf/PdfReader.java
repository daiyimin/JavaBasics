package com.ericsson.tc.util.pdf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.ericsson.tc.ConfigTool;

public class PdfReader {
	private String pdfFilePath;
	
	public PdfReader(String pdfFilePath) {
		this.pdfFilePath = pdfFilePath;
	}
	/**
	 * simply reader all the text from a pdf file. You have to deal with the
	 * format of the output text by yourself. 2008-2-25
	 * 
	 * @param pdfFilePath
	 *            file path
	 * @return all text in the pdf file
	 */
	public String getTextFromPDF() {
		String result = null;
		FileInputStream is = null;
		PDDocument document = null;
		try {
			is = new FileInputStream(pdfFilePath);
			PDFParser parser = new PDFParser(is);
			parser.parse();
			document = parser.getPDDocument();
			PDFTextStripper stripper = new PDFTextStripper();
			result = stripper.getText(document);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		ConfigTool.loadConfig();
		PdfReader reader = new PdfReader("C:\\temp\\2400-CAX1053952-5_EN_G_PDFV1R4.pdf");
		String str = reader.getTextFromPDF();
		System.out.println(str);

	}
}