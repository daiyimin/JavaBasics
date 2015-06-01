package com.ericsson.tc;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ericsson.tc.util.cmdline.ExecCmd;

public class TestLog4j {
	
	private static void loadConfig() {
		String localDir = System.getProperty("user.dir") + "/";
		PropertyConfigurator.configure(localDir + "log4j.properties");
	}
	
	private static void testPDI() {
		Logger logger = Logger.getLogger(TestLog4j.class);
        String[] cmd = new String[] { "cmd.exe", "/C", "pdi getdoc -env Prod -Format  MSWORD_DOC -user eyimdai -password Luoxia014 -DocNo 2400-5/CAX1055181 -Lang EN -Rev A -out c:\\temp\\ -AutoSave" };
        ExecCmd exec = new ExecCmd(cmd);
        exec.exec();
        
        List<String> l = exec.getCmdResult();
        for(String line:l) {
        	logger.info(line);
        }
	}

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(TestLog4j.class);
		loadConfig();

		logger.info("test info");
	}
}
