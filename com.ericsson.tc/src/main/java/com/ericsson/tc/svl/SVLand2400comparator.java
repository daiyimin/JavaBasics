package com.ericsson.tc.svl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.ericsson.tc.ConfigTool;
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
		
		// when the product number doesn't have 2400 doc, we check if the encrypt algorithm in SVL is all empty
		// if is empty, suppose they match, otherwise don't match
		if (doc2400Bean == null) {
			if (!compareCsvString(svl3ppBean.getSymmetric(), "NA", "No 2400")) {
				match = false;
			}
			if (!compareCsvString(svl3ppBean.getAsymmetric(), "NA", "No 2400")) {
				match = false;
			}
			if (!compareCsvString(svl3ppBean.getHash(), "NA", "No 2400")) {
				match = false;
			}
			return match;
		}		

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
	 * 		true if match, false if not match
	 */
	public boolean compareCsvString(String svlStr, String doc2400Str, String type) {
		if (svlStr == null && doc2400Str == null) {
			logger.info(type + ",\"" + svlStr + "\",\"" + doc2400Str
					+ "\",OK");
			return true;
		}
		
		// NA and null and - and empty are equal
		if (svlStr == null || "".equals(svlStr)) {
			if ("N/A".equals(doc2400Str) || "NA".equals(doc2400Str) || "-".equals(doc2400Str) 
					|| "".equals(doc2400Str) || doc2400Str.contains("Unknown") ) {
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
		if(!compareCsvString(hashInSVL, hashIn2400, "Hash")) {
			match = false;
		}
		return match;
	}
	
	
	public static void main(String...strings ) throws IOException {
/*		ConfigTool.loadConfig();
		
//		String encryptAlgo = "3-Way (96), 3DES(168), AES(256), Anubis(320), Blowfish(448), Camellia(256), CAST5(128), CAST6(256), CMEA(64), DEAL(256), DES(56),  GOST(256), IDEA(128), LOKI91(64), Lucifer(128), MARS(256), MISTY1(128), MMB(128), NewDES(120), RC2(1024), RC4(2048), RC5(2040), RC6(256), SAFER(128), SEED(128), Serpent(256), Skipjack(80), Square(128), TEA(128), Twofish(256), XTEA(128), ISAAC(8192), ORYX(96),  Salsa20(256), SEAL(160), WAKE(256),DSA(4096), DH(8192), RSA(4096), ElGamal(4096), ECDH(571), ECDHE(571), ECDSA(571),SHA1, SHA2, MD5, Tiger, MD2, MD4, Whirlpool, RIPEMD, RIPEMD160, Haval, Snefru";
		
		String encryptAlgo = "Symmetric: RC2(1024),3DES(168),AES(256),Camellia(256),CAST6(256),SEED(128),DES(56),RC5(2040),Twofish(256),Blowfish(448),VMPC(128),XTEA(128),RC4(2048),NOEKEON(128),Skipjack(80),Salsa20(256),RC6(256),PBE(56) Asymmetric: RSA(4096),DH(8192),ElGamal(4096),GOST(256),MQV(512),DSA(4096),ECNR(571),ECDSA(571),ECDH(571),ECMQV(571) Hash: SHA,RipeMD160,SHA2,MD4,SHA1,RIPEMD,MD5,MD2,Whirlpool"; 
		SVL3PPBean svlbean = new SVL3PPBean();
		svlbean.setEncryptAlgo(encryptAlgo);
		
//		String encryptAlgo2 = "Symmetric:3-Way (96), 3DES(168), AES(256), Anubis(320), Blowfish(448), Camellia(256), CAST5(128), CAST6(256), CMEA(64), DEAL(256), DES(56),  GOST(256), IDEA(128), LOKI91(64), Lucifer(128), MARS(256), MISTY1(128), MMB(128), NewDES(120), RC2(1024), RC4(2048), RC5(2040), RC6(256), SAFER(128), SEED(128), Serpent(256), Skipjack(80), Square(128), TEA(128), Twofish(256), XTEA(128), ISAAC(8192), ORYX(96),  Salsa20(256), SEAL(160), WAKE(256) Asymmetric: DSA(4096), DH(8192), RSA(4096),ElGamal(4096), ECDH(571), ECDHE(571), ECDSA(571) Hash:SHA1, SHA2, MD5, Tiger, MD2, MD4, Whirlpool, RIPEMD, RIPEMD160, Haval, Snefru";
		
		String encryptAlgo2 =  "Symmetric:	RC2 (1024), 3DES (168), AES (256), Camellia (256), CAST6 (256), SEED (128), DES (56), RC5 (2040), Twofish (256), Blowfish (448), VMPC (128), XTEA (128), RC4 (2048), NOEKEON (128), Skipjack (80), Salsa20 (256), RC6 (256), PBE (56)  Asymmetric: RSA (4096), DH (8192), ElGamal (4096), GOST (256), MQV (512), DSA (4096), ECNR (571), ECDSA (571), ECDH (571), ECMQV (571) Hash:	SHA, RipeMD160, SHA2, MD4, SHA1, RIPEMD, MD5, MD2, Whirlpool";
		SVL3PPBean svlbean2 = new SVL3PPBean();
		svlbean2.setEncryptAlgo(encryptAlgo);

		SVLand2400comparator comparator = new SVLand2400comparator(null, null);
		
		boolean match = true;		
		if(!comparator.compareCsvString(svlbean.getSymmetric(), svlbean2.getSymmetric(), "Symmetric")) {
			match = false;
		} 
		if(!comparator.compareCsvString(svlbean.getAsymmetric(), svlbean2.getAsymmetric(), "Asymmetric")) {
			match = false;
		} 
		if(!comparator.compareCsvString(svlbean.getHash(), svlbean2.getHash(), "Hash")) {
			match = false;
		}
*/	
		
		SVLand2400comparator comparator = new SVLand2400comparator(null, null);
		if(!comparator.compareCsvString("", "N/A", "Hash")) {
			System.out.println("Why");
		}
	}

}
