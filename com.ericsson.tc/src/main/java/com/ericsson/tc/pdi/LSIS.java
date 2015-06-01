package com.ericsson.tc.pdi;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ericsson.tc.ConfigTool;
import com.ericsson.tc.util.cmdline.ExecCmd;
import com.opencsv.CSVReader;

/**
 * LSIS execute pdi lsis command and return information structure of product
 * @author eyimdai
 *
 */
public class LSIS {
	private Logger logger = Logger.getLogger(LSIS.class);
	private String[] cmd;
	
	/**
	 * For the meaning of each attributes, please refer to the output of pdi lsis
	 */
	private static final String CHILD_NO_E = "ChildNoE";
	private static final String CHILD_REVISION = "ChildRevision";
	private static final String CHILD_LANGUAGE = "ChildLanguage";
	
	private String childNoE;
	private String childRevision;
	private String childLanguage;
	
	public String getChildNoE() {
		return childNoE;
	}

	public String getChildRevision() {
		return childRevision;
	}

	public String getChildLanguage() {
		return childLanguage;
	}
	
	public LSIS(String prodNo, String rstate) {
		String lsis = "pdi lsis -prodno " + prodNo
				+ " -rstate " + rstate
				+ " -ofmt CSV";
        cmd = new String[] { "cmd.exe", "/C", lsis };
	}

	public LSIS(String[] cmd) {
		this.cmd = cmd;
	}
	
	public void exec() throws IOException {
        ExecCmd exec = new ExecCmd(cmd);
        exec.exec();
        
        StringBuffer sb = new StringBuffer();
        List<String> l = exec.getCmdResult();
        for (String s: l) {
        	sb.append(s);
        	sb.append("\n");
        }
        parseResponse(sb.toString());
	}
	
	/**
	 * pdi lsis response is a string in CSV format <br/>
	 * parse it using openCSV, and save the 2400 document information
	 * @param response
	 * @throws IOException
	 */
	private void parseResponse(String response) throws IOException {
		logger.info("Start to parse pdi lsis response:\n " + response);
        StringReader reader = new StringReader(response);
		CSVReader csvReader = new CSVReader(reader);
		
		Map<String, Integer> csvIndex = new HashMap<String, Integer>();

		// read heading line
		String[] strs;
		try {
			strs = csvReader.readNext();
		} catch (IOException ex) {
			logger.error("Fail to read heading line");
			throw ex;
		}
		int colNum = strs.length;
		if (strs != null && colNum > 0) {
			// put CSV column names into csvIndex
			for (int i = 0; i < colNum; i++) {
				String str = strs[i];
				if (null != str && !str.equals("")) {
					csvIndex.put(strs[i], new Integer(i));
				}
			}
		}
		
		List<String[]> list;
		try {
			list = csvReader.readAll();
		} catch (IOException ex) {
			logger.error("Fail to read csv");
			throw ex;
		}
		
		// find 2400 line, and save related info
		for (String[] line : list) {
			childNoE = line[csvIndex.get(CHILD_NO_E)];
			if (childNoE.startsWith("2400")) {
				childNoE = line[csvIndex.get(CHILD_NO_E)];
				childNoE = childNoE.replaceAll(" ", "");
				childRevision = line[csvIndex.get(CHILD_REVISION)];
				childLanguage = line[csvIndex.get(CHILD_LANGUAGE)];
				if (childLanguage.startsWith("U")) {
					childLanguage = childLanguage.substring(1);
				}
				break;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Logger logger = Logger.getLogger(LSIS.class);
		ConfigTool.loadConfig();
		
//        String[] cmd = new String[] { "cmd.exe", "/C", "pdi lsis -prodno 79/CAX1053952/1 -rstate R1B -ofmt CSV" };
//		LSIS lsis = new LSIS(cmd);
		
		LSIS lsis = new LSIS("79/CAX1053952/1", "R1B");
		try {
			lsis.exec();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		System.out.println(lsis.getChildNoE());
		System.out.println(lsis.getChildLanguage());
		System.out.println(lsis.getChildRevision());
	}

}
