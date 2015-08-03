package com.ericsson.tc.doc2400;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.ericsson.tc.ConfigTool;
import com.ericsson.tc.util.pdf.PdfReader;

public class Doc2400Parser {
	public static final String SW_NAME_VERSION_START = "Software name and Version / \r\nFunction designation:"; 
	public static final String SW_NAME_VERSION_END = "Design responsible organization:";
	public static final String PRODUCT_NAME_START = "Product name/description";
	public static final String PRODUCT_NAME_END = "Is the product a part of a";
	public static final String PAPE_EDGE = "Ericsson Internal";
	
	public static final String EU_ECCN = "EU ECCN code:";
	public static final String US_ECCN = "US ECCN code:";
	public static final String EU_ECCN_2 = "EU: ECCN CODE";
	public static final String US_ECCN_2 = "US: ECCN CODE";
	public static final String US_ECCN_END = "Encryption:";
	public static final String US_ECCN_END_2 = "AEC Code";
	public static final String US_ECCN_END_3 = "Reason for control:";

	public static final String PROTOCOL_START = "3.4 Protocols \r\nFollowing list of secure/encrypted protocols are included in the product:";
	public static final String PROTOCOL_END = "3.5 Other information ";
	public static final String PROTOCOL_START_2 = "Security \r\nProtocol";
	public static final String PROTOCOL_END_2 = "Mass \r\nMarket \r\nClassified";

	public static final String ORIG_COUNTRY_START = "Design Origin Countries for SW (to \r\nrecord the history):";
	public static final String ORIG_COUNTRY_END = "For Country code list see the instruction ";
	public static final String ORIG_COUNTRY_START_2 = "Design origin design \r\n(Country List)";
	public static final String ORIG_COUNTRY_END_2 = EU_ECCN_2;
	public static final String ORIG_COUNTRY_START_3 = "Design origin \r\n(Country List)";
	public static final String ORIG_COUNTRY_END_3 = EU_ECCN_2;
	public static final String ORIG_COUNTRY_START_4 = ORIG_COUNTRY_START;
	public static final String ORIG_COUNTRY_END_4 = "Country List";
	public static final String US_ORIGIN_START = "US-origin design, YES or NO";
	public static final String US_ORIGIN_END = EU_ECCN_2;

	
	public static final String CCATS_START = "CCATS or ERN";
	public static final String CCATS_END = "Mass Market Classified";
	public static final String CCATS_START_2 = "CCATS:";
	public static final String CCATS_END_2 = PROTOCOL_START_2;

	public static final String SYMMETRIC = "Symmetric:";
	public static final String ASYMMETRIC = "Asymmetric:";
	public static final String HASH = "Hash:";
	public static final String HASH_END_1 = CCATS_START;
	public static final String HASH_END_2 = "Enter or mark function of encryption?";
	public static final String HASH_END_3 = "3.2 Usage of encryption in the product";
	public static final String HASH_END_4 = "License Exceptions";
	
	private PdfReader reader;
	private Doc2400Bean bean;
	
	private Logger logger = Logger.getLogger(Doc2400Parser.class);
	
	public Doc2400Parser(PdfReader reader) {
		this.reader = reader;
		bean = new Doc2400Bean();
	}
	
	public Doc2400Bean getDoc2400Bean() {
		return bean;
	}
	
	public void parse() {
		String doc2400Text = reader.getTextFromPDF();
		parseSwNameAndVer(doc2400Text);
		parseEuEccn(doc2400Text);
		parseUsEccn(doc2400Text);
		parseProtocol(doc2400Text);
		parseOrigCountry(doc2400Text);
//		parseCcats(doc2400Text);
		parseSymmetric(doc2400Text);
		parseAsymmetric(doc2400Text);
		parseHash(doc2400Text);
	}
	
	private String parseValue(String doc2400Text, String start, String end) {
		int startIdx = doc2400Text.indexOf(start);
		if (startIdx == -1) {
			return null;
		}
		int endIdx = doc2400Text.indexOf(end, startIdx + start.length());
		if (endIdx == -1) {
			return null;
		}
		
		String val;
		// when the string is close to page edge, we need to use page edge as delimitor
		int edgeIdx = doc2400Text.indexOf(PAPE_EDGE, startIdx);
		if (edgeIdx != -1 && edgeIdx < endIdx) {
			val = doc2400Text.substring(startIdx + start.length(), edgeIdx);
		} else {
			val = doc2400Text.substring(startIdx + start.length(), endIdx);
		}

		return val.trim();
	}
	
	public void parseSwNameAndVer(String doc2400Text) {
		String val = parseValue(doc2400Text, SW_NAME_VERSION_START, SW_NAME_VERSION_END);
		if (val == null) {
			val = parseValue(doc2400Text, PRODUCT_NAME_START, PRODUCT_NAME_END);
		}
		bean.setSwNameAndVer(val);
	}
	
	public void parseEuEccn(String doc2400Text) {
		String val = parseValue(doc2400Text, EU_ECCN, US_ECCN);
		if (val == null) {
			val = parseValue(doc2400Text, EU_ECCN_2, US_ECCN_2);
		}
		bean.setEuEccn(val);
	}
	
	public void parseUsEccn(String doc2400Text) {
		String val = parseValue(doc2400Text, US_ECCN, US_ECCN_END);
		if (val == null) {
			val = parseValue(doc2400Text, US_ECCN_2, US_ECCN_END_2);
		}
		if (val == null) {
			val = parseValue(doc2400Text, US_ECCN_2, US_ECCN_END_3);
		}
		bean.setUsEccn(val);
	}

	public void parseProtocol(String doc2400Text) {
		String val = parseValue(doc2400Text, PROTOCOL_START, PROTOCOL_END);
		if (val == null) {
			val = parseValue(doc2400Text, PROTOCOL_START_2, PROTOCOL_END_2);
		}
		bean.setProtocol(val);
	}
	
	public void parseOrigCountry(String doc2400Text) {
		String val = parseValue(doc2400Text, ORIG_COUNTRY_START, ORIG_COUNTRY_END);
		if (val == null) {
			val = parseValue(doc2400Text, ORIG_COUNTRY_START_2, ORIG_COUNTRY_END_2);
		}
		if (val == null) {
			val = parseValue(doc2400Text, ORIG_COUNTRY_START_3, ORIG_COUNTRY_END_3);
		}
		if (val == null) {
			val = parseValue(doc2400Text, ORIG_COUNTRY_START_4, ORIG_COUNTRY_END_4);
		}
		if (val == null) {
			val = parseValue(doc2400Text, US_ORIGIN_START, US_ORIGIN_END);
			if ("yes".equals(val.toLowerCase())) {
				val = "US";
			}
		}

		bean.setOrigCountry(val);
	}

	public void parseCcats(String doc2400Text) {
		String val = parseValue(doc2400Text, CCATS_START, CCATS_END);
		if (val == null) {
			val = parseValue(doc2400Text, CCATS_START_2, CCATS_END_2);
		}
		bean.setCcats(val);
	}
	
	public void parseSymmetric(String doc2400Text) {
		String val = parseValue(doc2400Text, SYMMETRIC, ASYMMETRIC);
		bean.setSymmetric(val);
	}
	
	public void parseAsymmetric(String doc2400Text) {
		String val = parseValue(doc2400Text, ASYMMETRIC, HASH);
		bean.setAsymmetric(val);
	}

	public void parseHash(String doc2400Text) {
		String val = parseValue(doc2400Text, HASH, HASH_END_1);
		if (val == null) {
			val = parseValue(doc2400Text, HASH, HASH_END_2);
		}
		if (val == null) {
			val = parseValue(doc2400Text, HASH, HASH_END_3);
		}
		if (val == null) {
			val = parseValue(doc2400Text, HASH, HASH_END_4);
		}
		bean.setHash(val);
	}

	public static void main(String[] args) throws IOException {
		ConfigTool.loadConfig();
		PdfReader reader = new PdfReader("C://Temp//2400-1_CAX1055562_EN_A_PDFV1R5.pdf");
		Doc2400Parser parser = new Doc2400Parser(reader);
		parser.parse();
		Doc2400Bean bean = parser.getDoc2400Bean();
		
		System.out.println(bean.getSwNameAndVer());
		System.out.println(bean.getEuEccn());
		System.out.println(bean.getUsEccn());
		System.out.println(bean.getProtocol());
		System.out.println(bean.getOrigCountry());
//		System.out.println(bean.getCcats());
		System.out.println(bean.getSymmetric());
		System.out.println(bean.getAsymmetric());
		System.out.println(bean.getHash());
	}

}
