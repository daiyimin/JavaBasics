package com.ericsson.uml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.sourceforge.plantuml.SourceStringReader;

public class Main {
	public Map<String, String> configs;
	private String proclogfile;
	private String proclogId;
	
	public Main() throws IOException {
		loadConfig();
	}
	
	private void loadConfig() throws IOException {
		String localDir = System.getProperty("user.dir") + "/";
		Properties prop = new Properties();
		configs = new HashMap<String, String>();
		prop.load(new FileInputStream(localDir + Constants.CONFIG_FILENAME));
		for(Object key: prop.keySet()) {
			configs.put(key.toString(), prop.get(key).toString());
		}
		configs.put("localdir", localDir);
	}
	
	private void convertUML() throws IOException {
		ProclogReader reader = new ProclogReader(proclogfile);
		reader.readProclog(proclogId);
		
		String uml = reader.getUML();
		System.out.println(uml);
		
		String pngfile = configs.get("outputdir") + "/" + configs.get("outputfile");
		PlantUMLUtil.transfromStringToUML(uml, pngfile);
	}
	
	/**
	 * 
	 * @param args
	 * 		arg0: proclog file
	 * 		arg1: proclogId
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Main myMain = new Main();
		new ProclogSelFrame(myMain.configs);
//		myMain.convertUML();
	}

}
