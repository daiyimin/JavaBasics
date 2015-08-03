package com.ericsson.tc.util.cmdline;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Drain command result and store it.
 * @author eyimdai
 *
 */
class StreamDrainer implements Runnable {
	private Logger logger = Logger.getLogger(StreamDrainer.class);
    private InputStream ins;
    private List<String> result;

    public StreamDrainer(InputStream ins) {
        this.ins = ins;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(ins));
            String line = null;
            result = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
            	result.add(line);
            }
        } catch (Exception e) {
			logger.error("Fail to read result of Windows cmd");
            e.printStackTrace();
        }
    }
    
    public List<String> getResult() {
    	return result;
    }

}

/**
 * ExecCmd executes a Windows command synchronously, and store the result
 * @author eyimdai
 */
public class ExecCmd {
	private String[] cmd = null;
	private List<String> cmdResult = null;
	private Logger logger = Logger.getLogger(ExecCmd.class);

	/**
	 * @param cmd
	 *            command to be executed
	 */
	public ExecCmd(String[] cmd) {
		this.cmd = cmd;
	}

	/**
	 * execute Windows command
	 */
	public void exec() {
		try {
			Process process = Runtime.getRuntime().exec(cmd);

			StreamDrainer stdout = new StreamDrainer(process.getInputStream());
			StreamDrainer errout = new StreamDrainer(process.getErrorStream());
			new Thread(stdout).start();
			new Thread(errout).start();
			
			process.getOutputStream().close();
			int exitValue = process.waitFor();
			
			if (exitValue == 0) {
				cmdResult = stdout.getResult();
			} else {
				cmdResult = errout.getResult();
			}
		} catch (Exception e) {
			logger.error("Fail to execute cmd: " + cmd);
			e.printStackTrace();
		}
	}
	
	/**
	 * get command result
	 * @return
	 */
	public List<String> getCmdResult() {
		return cmdResult;
	}
}
