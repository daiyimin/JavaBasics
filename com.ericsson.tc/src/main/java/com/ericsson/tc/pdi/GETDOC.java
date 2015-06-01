package com.ericsson.tc.pdi;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ericsson.tc.ConfigTool;
import com.ericsson.tc.util.cmdline.ExecCmd;

/**
 * GetDoc executes pdi getdoc and return response
 * @author eyimdai
 *
 */
public class GETDOC {
	private Logger logger = Logger.getLogger(GETDOC.class);
	
	String[] cmd;
	
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

	public void exec() {
        ExecCmd exec = new ExecCmd(cmd);
        exec.exec();
        
        List<String> l = exec.getCmdResult();
        StringBuffer sb = new StringBuffer();
        for (String s: l) {
        	sb.append(s);
        }
        if (!sb.toString().contains("Content Saved")) {
        	logger.error("Fail execute " + cmd);
        }
	}
	
	public static void main(String[] args) throws IOException {
		Logger logger = Logger.getLogger(GETDOC.class);
		ConfigTool.loadConfig();
		
		GETDOC getdoc = new GETDOC("2400-CAX1053952-5", "en", "G");
		getdoc.exec();
	}
}
