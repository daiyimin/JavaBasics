package com.ericsson.tc.doc2400;

import org.apache.log4j.Logger;

public class Doc2400Bean {

	private Logger logger = Logger.getLogger(Doc2400Bean.class);
	
	private String swNameAndVer;
	private String euEccn;
	private String usEccn;
	private String protocol;
	private String origCountry;
	private String ccats;  // compare with bisAuth in SVL3PPBean
	private String symmetric;
	private String asymmetric;
	private String hash;
	
	public String getSwNameAndVer() {
		return swNameAndVer;
	}
	public void setSwNameAndVer(String swNameAndVer) {
		this.swNameAndVer = swNameAndVer;
	}
	public String getEuEccn() {
		return euEccn;
	}
	public void setEuEccn(String euEccn) {
		this.euEccn = euEccn;
	}
	public String getUsEccn() {
		return usEccn;
	}
	public void setUsEccn(String usEccn) {
		this.usEccn = usEccn;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getOrigCountry() {
		return origCountry;
	}
	public void setOrigCountry(String origCountry) {
		this.origCountry = origCountry;
	}
	public String getCcats() {
		return ccats;
	}
	public void setCcats(String ccats) {
		this.ccats = ccats;
	}
	public String getSymmetric() {
		return symmetric;
	}
	public void setSymmetric(String symmetric) {
		this.symmetric = replaceEmptyEncryptString(symmetric);
	}
	public String getAsymmetric() {
		return asymmetric;
	}
	public void setAsymmetric(String asymmetric) {
		this.asymmetric = replaceEmptyEncryptString(asymmetric);
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = replaceEmptyEncryptString(hash);
	}
	
	private String replaceEmptyEncryptString(String val) {
		if ("NA".equals(val) || 
			"N\\A".equals(val) ||
			"-".equals(val)) {
			return "";
		} else {
			return val;
		}
	}
}
