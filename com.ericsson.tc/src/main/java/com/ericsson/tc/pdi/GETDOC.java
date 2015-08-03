package com.ericsson.tc.pdi;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ericsson.tc.ConfigTool;
import com.ericsson.tc.util.cmdline.ExecCmd;

/**
 * GetDoc executes pdi getdoc and return response
 * @author eyimdai
 *
 */
public class GETDOC {
	private Logger logger = Logger.getLogger(GETDOC.class);
	
	private String[] cmd;
	private String resultFilename = null;
	
	public GETDOC(String docNo, String lang, String rev) {
		String getdoc = "pdi getdoc -env Prod -Format PDF " 
				+ " -user " + ConfigTool.getConfig("getdoc.user")
				+ " -password " + ConfigTool.getConfig("getdoc.password")
				+ " -DocNo " + docNo + " -Lang " + lang + " -Rev " + rev
				+ " -out " + ConfigTool.getConfig("getdoc.output.dir");
        cmd = new String[] { "cmd.exe", "/C", getdoc };
	}

	public GETDOC(String[] cmd) {
		this.cmd = cmd;
	}

	public void exec() throws IOException{
        ExecCmd exec = new ExecCmd(cmd);
        exec.exec();
        
        List<String> l = exec.getCmdResult();
        StringBuffer sb = new StringBuffer();
        for (String s: l) {
        	sb.append(s);
        }
        
        String response = sb.toString();
        if (!response.contains("Content Saved") &&
        		!response.contains("Successfully fetched the Eridoc file")) {
        	logger.error("Fail execute " + cmd[2]);
        	throw new IOException("Fail to execute " + cmd[2]);
        } else {
        	int start = response.indexOf("'");
        	int end = response.indexOf("'", start + 1);
        	resultFilename = response.substring(start + 1, end);
    		logger.info("2400 file is downloaded to: " + resultFilename);
        }
	}
	
	public String getResultFilename() {
		return resultFilename;
	}
	
	public static void main(String[] args) throws IOException {
		Logger logger = Logger.getLogger(GETDOC.class);
		ConfigTool.loadConfig();
		
		GETDOC getdoc = new GETDOC("2400-1/CAX1053785", "en", "A");
		getdoc.exec();
		
		System.out.print(getdoc.getResultFilename());
	}
}
