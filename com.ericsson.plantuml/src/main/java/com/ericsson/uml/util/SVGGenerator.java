package com.ericsson.uml.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.ericsson.uml.Constants;

public class SVGGenerator {
	private String svgString = "";
	private String filename = null;
	private int charPerLine = 80;
	private String linefeed = "\n";
	
	private int width;
	private int height;
	
	public SVGGenerator() {
	}
	
	public SVGGenerator(String filename) {
		this.filename = filename;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void addHead() {
		svgString += "<?xml version=\"1.0\" standalone=\"no\"?>\n" +
					"<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" " + 
					"\"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n" +
					"<svg style=\"width:@WIDTH@px;height:@HEIGHT@px;\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n" +
					"<g>\n";
	}
	
	public void addTail() {
		svgString += "</g>\n" +
					 "</svg>";
	}
	
	/**
	 * add hyperlink in svg
	 * @param x
	 * @param y
	 * @param text
	 * @param link
	 * @return y coordinate of next line
	 */
	public int addHyperLink(int x, int y, String text, String link) {
		svgString += "<a xlink:href=\"" + link + "\" xlink:title=\"" + text + "\">\n" +
					 "<text fill=\"blue\" font-family=\"sans-serif\" font-size=\"12pt\" " +
					 "font-weight=\"bold\" lengthAdjust=\"spacingAndGlyphs\" textLength=\"40\" " +
					 "x=\""+ x + "\" y=\""+ y + "\">" + text +
					 "</text>\n" +
					 "</a>\n";
		return y + 20;
	}
	
	/**
	 * Get indent
	 * @param inputLine
	 * @return
	 */
	private String getIndent(String inputLine) {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<inputLine.length(); i++) {
			if (inputLine.charAt(i) == ' ') {
				sb.append(' ');
			} else {
				break;
			}
		}
		return sb.toString();
	}
	
	private String encodeHTMLChar(String line) {
		String encodeStr = line.replaceAll("<", "&lt;");
		encodeStr = encodeStr.replaceAll("\"", "&quot;");
//		encodeStr = encodeStr.replaceAll(" ", "&nbsp;");
		return encodeStr;
	}
	
	/**
	 * Parse input text into multiple lines that will be shown in SVG
	 * Each line in input text will be broken into multiple lines in SVG which has max length: 80 chars 
	 * @param text
	 * @param seperator
	 * @return
	 * @throws Exception 
	 */	
	protected List<String> splitText(String text, String seperator) throws Exception {
		
		List<String> outputLineList = new ArrayList<String>();
		String[] inputLines = text.split(linefeed);
		for (String line: inputLines) {
			if (line.trim().isEmpty()) {
				continue; // skip empty line
			}
			
			String indent = getIndent(line);
			
			int count = 0;
			String outputLine = "";
			String[] strs = line.split(seperator);			
			
			for (String str: strs) {
				if (str.isEmpty()) {
					continue;
				}
				if (count + str.length() <= charPerLine) {
					if (count > 0) {
						outputLine += seperator;
					} else {
						count = indent.length();
						outputLine = indent;
					}
					count += str.length();
					outputLine += str;
				} else {
					// exceed 80 chars, create a new line
					if (!outputLine.trim().isEmpty()) {
						outputLine += seperator;
						outputLineList.add(encodeHTMLChar(outputLine));
					}
					count = str.length();
					outputLine = indent + " " + str;
				}
			}
			if (count > 0) {
				outputLineList.add(encodeHTMLChar(outputLine));
			}
		}
		return outputLineList;
	}
	
	/**
	 * Parse input text into multiple lines that will be shown in SVG
	 * Each line in input text will be broken into multiple lines in SVG which has max length: 80 chars 
	 * @param text
	 * @param sepeartor
	 * @return
	 * @throws Exception 
	 */	
	protected List<String> splitLDAPText(String text, String seperator) throws Exception {
		List<String> outputLineList = new ArrayList<String>();
		
		String line = null;
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<text.length(); i++) {
			if (sb.length() > charPerLine && line != null) {
				outputLineList.add(line);
				sb.replace(0, line.length(), "");
			}
			char curChar = text.charAt(i);
 			sb.append(curChar);
			if (seperator.indexOf(curChar) != -1) {
				line = sb.toString();
			}
		}
		if (sb.length() > 0) {
			outputLineList.add(sb.toString());
		}
		
		return outputLineList;
	}
	
	/**
	 * Parse input text into multiple lines that will be shown in SVG
	 * Each line in input text will be broken into multiple lines in SVG which has max length: 80 chars 
	 * @param text
	 * @param sepeartor
	 * @return
	 * @throws Exception 
	 */	
	protected List<String> splitTelnetText(String text, String seperator) throws Exception {
		List<String> outputLineList = new ArrayList<String>();
		
		String line = null;
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<text.length(); i++) {
			char curChar = text.charAt(i);
			if (curChar == '\n') {
				outputLineList.add(sb.toString());
				sb.delete(0, sb.length());
				continue;
			}
			
			if (sb.length() > charPerLine && line != null) {
				outputLineList.add(line);
//				sb.replace(0, line.length(), "");
				sb.delete(0, line.length());
			}
 			sb.append(curChar);
			if (seperator.indexOf(curChar) != -1) {
				line = sb.toString();
			}
		}
		if (sb.length() > 0) {
			outputLineList.add(sb.toString());
		}
		
		return outputLineList;
	}

	
	/**
	 * add text into svg<br/>
	 * font size 8pt, maximum 80 chars per line
	 * @param x
	 * @param y
	 * @param text
	 * @return y coordinate of next line
	 * @throws Exception 
	 */
	public int addText(int x, int y, String text, String protocol) throws Exception {
		List<String> lines = null;
		if (Constants.CAI3G_PROTOCOL.equals(protocol)) {
			String inputText = XMLFormat.formatXML(text);
			lines = splitText(inputText, " ");
		} else if (Constants.LDAP_PROTOCOL.equals(protocol)) {
			lines = splitLDAPText(text, ",:\n");
		} else if (Constants.TELNET_PROTOCOL.equals(protocol)) {
			lines = splitTelnetText(text, ",:");
		}
		for (int i=0; i<lines.size(); i++) {
			String line = lines.get(i);
			String indent = getIndent(line);
			int xCordinate = x + indent.length()*8;
			int yCordinate = y + i*12;
			svgString += "<text style=\"fill:black;font-size:10pt\" font-family=\"sans-serif\" "
					+ "x=\""+ xCordinate + "\" y=\""+ yCordinate + "\">" + line.trim() + "</text>\n";
		}
		return y + lines.size()*12;
	}
	
	/**
	 * add Heading 0<br/>
	 * font size 12pt, maximum 80 chars per line
	 * @param x
	 * @param y
	 * @param text
	 * @return y coordinate of next line
	 * @throws Exception 
	 */
	public int addH0(int x, int y, String text) throws Exception {
		svgString += "<text fill=\"green\" font-family=\"sans-serif\" font-size=\"12pt\" " +
				 "font-weight=\"bold\" lengthAdjust=\"spacingAndGlyphs\" " +
				 "x=\""+ x + "\" y=\""+ y + "\">" + text +
				 "</text>\n";
		return y + 20;
	}

	
	public String getSVGContent() {
		svgString = svgString.replaceAll("@WIDTH@", new Integer(width).toString());
		svgString = svgString.replaceAll("@HEIGHT@", new Integer(height).toString());
		return svgString;
	}
	
	/**
	 * Create SVG file using svgString
	 * @throws IOException 
	 */
	public void createSVG() throws IOException {
		File f = new File(filename);
		OutputStream os = new FileOutputStream(f);
		PrintWriter writer = new PrintWriter(os);
		writer.print(getSVGContent());
		writer.flush();
		os.close();
	}	
	
/*	public static void main(String[] args) throws Exception {
        String longXMLString = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cai3=\"http://schemas.ericsson.com/cai3g1.2/\" xmlns:ns=\"http://schemas.ericsson.com/pg/hlr/13.5/\"><soapenv:Header><cai3:SessionId xmlns:cai3=\"http://schemas.ericsson.com/cai3g1.2/\" xmlns:ns=\"http://schemas.ericsson.com/pg/hlr/13.5/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">6f133e1cf7944392bdbd8b2b35be67b4</cai3:SessionId></soapenv:Header><soapenv:Body>\n      <cai3:Set xmlns:cai3=\"http://schemas.ericsson.com/cai3g1.2/\" xmlns:ns=\"http://schemas.ericsson.com/pg/hlr/13.5/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xml=\"http://www.w3.org/XML/1998/namespace\">\n         <cai3:MOType>SubscriberData@http://schemas.ericsson.com/pg/hlr/13.5/</cai3:MOType>\n         <cai3:MOId>\n            <ns:msisdn>494601000007</ns:msisdn>\n         </cai3:MOId>\n         <cai3:MOAttributes>\n            <ns:SetSubscriberData msisdn=\"494601000007\">\n               <ns:sud>PWD-******</ns:sud>\n            </ns:SetSubscriberData>\n         </cai3:MOAttributes>\n      </cai3:Set></soapenv:Body></soapenv:Envelope>";
		String longLDAPString = "LDAP Modify (dn: serv=CSPS,mscId=314587c8e13f4bb884929bf6ca0f3684,ou=multiSCs,dc=operator,dc=com Attributes: Attribute name: CDC, values: [7], operation: REPLACE, Attribute name: SPN, values: [1], operation: REPLACE, Attribute name: SPNTS10FNUM, values: 919499290000F0, operation: REPLACE, Attribute name: SPNTS10CCREL, values: [0], operation: REPLACE, Attribute name: SPNTS10ZCREL, values: [0], operation: REPLACE, Attribute name: SPNTS10ST, values: [6], operation: REPLACE)";
        
		SVGGenerator svg = new SVGGenerator("c:\\Ericsson\\test1.svg");
		svg.setWidth(800);
		svg.setHeight(600); 
		svg.addHead();
		svg.addHyperLink(1, 15, "BACK", "file:///C:/Ericsson/test.svg");
		svg.addText(1, 35, longXMLString, Constants.CAI3G_PROTOCOL);
//		svg.addText(1, 35, longLDAPString, Constants.LDAP_PROTOCOL);
		svg.addTail();
		
		svg.createSVG();
		System.out.println(svg.getSVGContent());
	}
*/
}
