package com.ericsson.tc.svl;

import org.apache.log4j.Logger;

import com.ericsson.tc.doc2400.CountryCode;
import com.ericsson.tc.doc2400.Doc2400Bean;

public class SVLand2400comparator {
	private Logger logger = Logger.getLogger(SVLand2400comparator.class);

	private SVL3PPBean svl3ppBean;
	private Doc2400Bean doc2400Bean;

	public SVLand2400comparator(SVL3PPBean svl3ppBean, Doc2400Bean doc2400Bean) {
		this.svl3ppBean = svl3ppBean;
		this.doc2400Bean = doc2400Bean;
	}

	/**
	 * Compare the SVL3PPBean and Doc2400Bean
	 * 
	 * @return true if match<br/> 
	 * false if don't match
	 */
	public boolean compare() {
		boolean match = true;

		logger.info("Start to compare " + svl3ppBean.getSwName());
		logger.info("Attribute,SVL,2400,Result");
		if (svl3ppBean.getEuEccn().equals(doc2400Bean.getEuEccn())) {
			logger.info("EU ECCN," + svl3ppBean.getEuEccn() + ","
					+ doc2400Bean.getEuEccn() + ",OK");
		} else {
			logger.info("EU ECCN," + svl3ppBean.getEuEccn() + ","
					+ doc2400Bean.getEuEccn() + ",NOK");
			match = false;
		}
		if (svl3ppBean.getUsEccn().equals(doc2400Bean.getUsEccn())) {
			logger.info("US ECCN," + svl3ppBean.getUsEccn() + ","
					+ doc2400Bean.getUsEccn() + ",OK");
		} else {
			logger.info("US ECCN," + svl3ppBean.getUsEccn() + ","
					+ doc2400Bean.getUsEccn() + ",NOK");
			match = false;
		}
		if (CountryCode.compareCountryCode(svl3ppBean.getOrigCountry(), doc2400Bean.getOrigCountry())) {
			logger.info("Orig country," + svl3ppBean.getOrigCountry() + ","
					+ doc2400Bean.getOrigCountry() + ",OK");
		} else {
			match = false;
			logger.info("Orig country," + svl3ppBean.getOrigCountry() + ","
					+ doc2400Bean.getOrigCountry() + ",NOK");
		}
		if (!compareProtocol()) {
			match = false;
		}
		if (!compareEncryptAlgorithm()) {
			match = false;
		}

		return match;
	}

	private boolean compareProtocol() {
		boolean match = true;
		if (!compareCsvString(svl3ppBean.getProtocol(), doc2400Bean.getProtocol(), "Protocol")) {
			match = false;
		}
		
		return match;
	}

	/**
	 * Compare two csv format string(dot separated string)<br/>
	 * Protocol, EncrpytAlgorithm are all csv format string
	 * @param svlStr
	 * @param doc2400Str
	 * @param type
	 * @return
	 */
	private boolean compareCsvString(String svlStr, String doc2400Str, String type) {
		if (svlStr == null && doc2400Str == null) {
			logger.info(type + ",\"" + svlStr + "\",\"" + doc2400Str
					+ "\",OK");
			return true;
		}
		
		// NA and null and - and empty are equal
		if (svlStr == null ) {
			if ("N/A".equals(doc2400Str) || "NA".equals(doc2400Str) || "-".equals(doc2400Str) 
					|| "".equals(doc2400Str)) {
				logger.info(type + ",\"" + svlStr + "\",\"" + doc2400Str
						+ "\",OK");
				return true;
			} else {
				logger.info(type + ",\"" + svlStr + "\",\"" + doc2400Str
						+ "\",NOK");
				return false;
			}
		}
		// NA and null and - and empty are equal
		if (doc2400Str == null ) {
			if ("N/A".equals(svlStr) || "NA".equals(svlStr) || "-".equals(svlStr) || "".equals(svlStr)) {
				logger.info(type + ",\"" + svlStr + "\",\"" + doc2400Str
						+ "\",OK");
				return true;
			} else {
				logger.info(type + ",\"" + svlStr + "\",\"" + doc2400Str
						+ "\",NOK");
				return false;
			}
		}

		
		// when comes to here, both svlStr and doc2400Str are not null		
		svlStr = svlStr.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "");
		doc2400Str = doc2400Str.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "");
		
		String[] svlArr = svlStr.split(","); 
		String[] doc2400Arr = doc2400Str.split(","); 
		
		boolean match = true;
		// check if doc2400Arr contains svlArr
		for (String outer : svlArr) {
			boolean found = false;
			for (String inner : doc2400Arr) {
				if (outer.equals(inner)) {
					found = true;
					break;
				}
			}
			if (!found) {
				match = false;
				break;
			}
		}
		// check if svlArr contains doc2400Arr
		for (String outer : doc2400Arr) {
			boolean found = false;
			for (String inner : svlArr) {
				if (outer.equals(inner)) {
					found = true;
					break;
				}
			}
			if (!found) {
				match = false;
				break;
			}
		}
		
		if (match) {
			logger.info(type + ",\"" + svlStr + "\",\"" + doc2400Str
					+ "\",OK");
		} else {
			logger.info(type + ",\"" + svlStr + "\",\"" + doc2400Str
					+ "\",NOK");
		}

		return match;
	}
	
	private boolean compareEncryptAlgorithm() {
		String symmetricInSVL = svl3ppBean.getSymmetric();
		String asymmetricInSVL = svl3ppBean.getAsymmetric();
		String hashInSVL = svl3ppBean.getHash();
		
		String symmetricIn2400 = doc2400Bean.getSymmetric();
		String asymmetricIn2400 = doc2400Bean.getAsymmetric();
		String hashIn2400 = doc2400Bean.getHash();

		boolean match = true;		
		if(!compareCsvString(symmetricInSVL, symmetricIn2400, "Symmetric")) {
			match = false;
		} 
		if(!compareCsvString(asymmetricInSVL, asymmetricIn2400, "Asymmetric")) {
			match = false;
		} 
		if(!compareCsvString(hashInSVL, hashInSVL, "Hash")) {
			match = false;
		}
		return match;
	}
}
