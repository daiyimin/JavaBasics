package com.ericsson.uml;

import java.util.Map;

public class ProclogBean {
	private String[] proclog;
	private Map<String, Integer> csvIndex;
	
	private String target;
	private String operation;
	private String fullrequest;
	private String fullresponse;
	private String responseCode;
	private String protocol;
	private String logType;
	private String rootLogId;
	private String subLogId;
	private String status;
	
	public ProclogBean(String[] proclog, Map<String, Integer> csvIndex) {
		this.proclog = proclog;
		this.csvIndex = csvIndex;
		parseProclog();
	}
	
	/**
	 * Get the cell value from a proclog by specifying cell name
	 * @param line   proclog
	 * @param csvCellName  cell name
	 * @return  cell value
	 */
	private String getCSVCellValue(String[] line, String csvCellName) {
		if (csvIndex == null) return null;
		return line[csvIndex.get(csvCellName)];
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getFullrequest() {
		return fullrequest;
	}
	public void setFullrequest(String fullrequest) {
		this.fullrequest = fullrequest;
	}
	public String getFullresponse() {
		return fullresponse;
	}
	public void setFullresponse(String fullresponse) {
		this.fullresponse = fullresponse;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getRootLogId() {
		return rootLogId;
	}
	public void setRootLogId(String rootLogId) {
		this.rootLogId = rootLogId;
	}
	public String getSubLogId() {
		return subLogId;
	}
	public void setSubLogId(String subLogId) {
		this.subLogId = subLogId;
	}
	
	private void parseProclog() {
		fullresponse = getCSVCellValue(proclog, Constants.FULLRESPONSE);
		fullrequest = getCSVCellValue(proclog, Constants.FULLREQUEST);
		logType = getCSVCellValue(proclog, Constants.LOGTYPE);
		operation = getCSVCellValue(proclog, Constants.OPERATION);
		protocol = getCSVCellValue(proclog, Constants.PROTOCOL);
		responseCode = getCSVCellValue(proclog, Constants.RESPCODE);
		rootLogId = getCSVCellValue(proclog, Constants.ROOTLOGID);
		subLogId = getCSVCellValue(proclog, Constants.SUBLOGID);
		target = getCSVCellValue(proclog, Constants.TARGET);
		status = getCSVCellValue(proclog, Constants.STATUS);
	}
}
