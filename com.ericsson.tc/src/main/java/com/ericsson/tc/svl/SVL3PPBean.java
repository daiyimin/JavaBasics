package com.ericsson.tc.svl;

import org.apache.log4j.Logger;

import com.ericsson.tc.doc2400.EncryptAlgorithm;

/**
 * The parameters of SVL3PPBean is attributes of one 3PP.<br/>
 * Each parameter is a column in SVL.<br/>
 * One SVL3PPBean instance represent a row in SVL
 * 
 * @author eyimdai
 *
 */
public class SVL3PPBean {
	// column names in SVL
	public static final String VENDOR_NAME = "Vendor Name or Ericsson Company Name, City & Country";
	public static final String SW_NAME = "SW name (FD in PRIM)";
	public static final String SW_WEB_ADDRESS = "SW web address";
	public static final String SW_TYPE = "SW Type";
	public static final String SW_VERSION = "SW version";
	public static final String ERICSSON_PRODUCT_NUMBER = "Ericsson\nProduct no including R-state";
	public static final String ERICSSON_3PP_LICENCE_PRODUCT_NUMBER = "Ericsson´s 3PP license product no";
	public static final String DESIGN_COUNTRY_OF_ORIGIN = "Design Country of origin";
	public static final String EU_ECCN = "EU ECCN";
	public static final String US_ECCN = "US ECCN";
	public static final String BIS_AUTHORIZATION = "BIS Authorization";
	public static final String ENCRYPTED_PROTOCOL = "Secure/Encrypted Protocol";
	public static final String ENCRYPT_ALGORITHM = "Encryption algorithm(s) and Encryption key length(s)";
	
	public static final String SYMMETRIC = "Symmetric";
	public static final String ASYMMETRIC = "Asymmetric";
	public static final String HASH = "Hash";


	public static final String[] COLUMNS = { VENDOR_NAME, SW_NAME,
			SW_WEB_ADDRESS, SW_TYPE, SW_VERSION, ERICSSON_PRODUCT_NUMBER,
			ERICSSON_3PP_LICENCE_PRODUCT_NUMBER, DESIGN_COUNTRY_OF_ORIGIN,
			EU_ECCN, US_ECCN, BIS_AUTHORIZATION, ENCRYPTED_PROTOCOL,
			ENCRYPT_ALGORITHM };

	private Logger logger = Logger.getLogger(SVL3PPBean.class);

	/**
	 * Vendor Name or Ericsson Company Name, City & Country
	 */
	private String vendorName;
	/**
	 * SW name (FD in PRIM)
	 */
	private String swName;
	/**
	 * SW web address
	 */
	private String swWebAddr;
	/**
	 * SW Type
	 */
	private String swType;
	/**
	 * SW version
	 */
	private String swVersion;
	/**
	 * Ericsson Product no including R-state
	 */
	private String ericProdNo;
	/**
	 * Ericsson's 3PP license product no
	 */
	private String eric3ppLicProdNo;
	/**
	 * Design Country of origin
	 */
	private String origCountry;
	/**
	 * EU ECCN
	 */
	private String euEccn;
	/**
	 * US ECCN
	 */
	private String usEccn;
	/**
	 * BIS Authorization
	 */
	private String bisAuth;
	/**
	 * Secure/Encrypted Protocol
	 */
	private String protocol;

	private String symmetricInSVL = null;
	private String asymmetricInSVL = null;
	private String hashInSVL = null;
	/**
	 * Encryption algorithm(s) and Encryption key length(s)
	 */
	private String encryptAlgo;
	
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getSwName() {
		return swName;
	}

	public void setSwName(String swName) {
		this.swName = swName;
	}

	public String getSwWebAddr() {
		return swWebAddr;
	}

	public void setSwWebAddr(String swWebAddr) {
		this.swWebAddr = swWebAddr;
	}

	public String getSwType() {
		return swType;
	}

	public void setSwType(String swType) {
		this.swType = swType;
	}

	public String getSwVersion() {
		return swVersion;
	}

	public void setSwVersion(String swVersion) {
		this.swVersion = swVersion;
	}

	public String getEricProdNo() {
		return ericProdNo;
	}

	public void setEricProdNo(String ericProdNo) {
		this.ericProdNo = ericProdNo;
	}

	public String getEric3ppLicProdNo() {
		return eric3ppLicProdNo;
	}

	public void setEric3ppLicProdNo(String eric3ppLicProdNo) {
		this.eric3ppLicProdNo = eric3ppLicProdNo;
	}

	public String getOrigCountry() {
		return origCountry;
	}

	public void setOrigCountry(String origCountry) {
		this.origCountry = origCountry;
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

	public String getBisAuth() {
		return bisAuth;
	}

	public void setBisAuth(String bisAuth) {
		this.bisAuth = bisAuth;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public String getSymmetric() {
		return symmetricInSVL;
	}
	
	public String getAsymmetric() {
		return asymmetricInSVL;
	}

	public String getHash() {
		return hashInSVL;
	}

	public String getEncryptAlgo() {
		return encryptAlgo;
	}
	
	private boolean setEncrytAlgoForEx1(String encryptAlgo) {
		boolean success = false;
		
		encryptAlgo = encryptAlgo.replaceAll(":", "");
		
		int symmetricIdxInSVL = encryptAlgo.indexOf(SVL3PPBean.SYMMETRIC);
		int asymmetricIdxInSVL = encryptAlgo.indexOf(SVL3PPBean.ASYMMETRIC);
		int hasIdxInSVL = encryptAlgo.indexOf(SVL3PPBean.HASH);
		if (symmetricIdxInSVL != -1) {
			if (asymmetricIdxInSVL != -1) {
				symmetricInSVL = encryptAlgo.substring(symmetricIdxInSVL
						+ SVL3PPBean.SYMMETRIC.length(), asymmetricIdxInSVL);
			} else if (hasIdxInSVL != -1) {
				symmetricInSVL = encryptAlgo.substring(symmetricIdxInSVL
						+ SVL3PPBean.SYMMETRIC.length(), hasIdxInSVL);
			} else {
				symmetricInSVL = encryptAlgo.substring(symmetricIdxInSVL
						+ SVL3PPBean.SYMMETRIC.length());
			}
			success = true;
		}
		if (asymmetricIdxInSVL != -1) {
			if (hasIdxInSVL != -1) {
				asymmetricInSVL = encryptAlgo.substring(asymmetricIdxInSVL
						+ SVL3PPBean.ASYMMETRIC.length(), hasIdxInSVL);
			} else {
				asymmetricInSVL = encryptAlgo.substring(asymmetricIdxInSVL
						+ SVL3PPBean.ASYMMETRIC.length());
			}
			success = true;
		}
		if (hasIdxInSVL != -1) {
			hashInSVL = encryptAlgo.substring(hasIdxInSVL + SVL3PPBean.HASH.length());
			success = true;
		}
		return success;
	}
	
	private void pickupEncryptAlgoInString(String encryptAlgo, String delimitor) {
		String [] words = encryptAlgo.split(delimitor);
		
		StringBuffer symmetric = new StringBuffer();
		StringBuffer asymmetric = new StringBuffer();
		StringBuffer hash = new StringBuffer();
		
		for (String word: words) {
			word = word.trim();
			if (EncryptAlgorithm.isSymmetric(word)) {
				symmetric.append(word).append(",");
			}
			if (EncryptAlgorithm.isAsymmetric(word)) {
				asymmetric.append(word).append(",");
			}
			if (EncryptAlgorithm.isHash(word)) {
				hash.append(word).append(",");
			}
		}
		
		if (symmetric.length() > 0) {
			symmetric.deleteCharAt(symmetric.length()-1); // remove last ","
			symmetricInSVL = symmetric.toString();
		}
		if (asymmetric.length() > 0) {
			asymmetric.deleteCharAt(asymmetric.length()-1); // remove last ","
			asymmetricInSVL = asymmetric.toString();
		}
		if (hash.length() > 0) {
			hash.deleteCharAt(hash.length()-1); // remove last ","
			hashInSVL = hash.toString();
		}
	}
	
	private boolean setEncrytAlgoForEx2(String encryptAlgo) {
		// check the first and last word in the string, if both of them are not encrypt algorithm, then this is example 2
		int idx = encryptAlgo.indexOf(" ");
		if (idx != -1) {
			if (EncryptAlgorithm.isEncryptAlgorithm(encryptAlgo.substring(0, idx))) {
				return false;
			}
		}
		idx = encryptAlgo.lastIndexOf(" ");
		if (idx != -1) {
			if (EncryptAlgorithm.isEncryptAlgorithm(encryptAlgo.substring(idx + 1))) {
				return false;
			}
		}
		
		// start to handle example 2
		pickupEncryptAlgoInString(encryptAlgo, " ");
		
		return true;
	}

	private boolean setEncrytAlgoForEx3(String encryptAlgo) {
		// start to handle example 3
		pickupEncryptAlgoInString(encryptAlgo, ",");
		
		return true;
	}

	public void setEncryptAlgo(String encryptAlgo) {
		// parse the SVL encrypt algorithm into three part
		if (encryptAlgo != null && encryptAlgo.length() > 0) {			
			// if there is nonsense content in the encrypt column, have to skip it
			if (encryptAlgo.equals("TBD")) {
				return;
			}
			
			this.encryptAlgo = encryptAlgo;
			
			// Normally, each encrypt part start with Symmetric, Asymmetric, Hash
			// Use them as delimiter to parse encrypt info
			/*
			 * Example 1:
			 * Symmetric: AES(256)
			 * Asymmetric: RSA(4096)
			 * Hash: MD5,SHA1,SHA
			 */
			if( setEncrytAlgoForEx1(encryptAlgo) ) {
				return;
			}
			
			// If no delimitor is found, and encrypt info is not empty, then we have to use encrypt algorithm name to find Symmetric, Asymmetric, Hash part
			/*
			 * Example 2:
			 * "SW uses MD5 from the
JCE library, based on this
the ECCN is EU=0 and
US=5D992B"
			*/
			if( setEncrytAlgoForEx2(encryptAlgo)) {
				return;
			}
			
			/*
			 * Example 3:
			 * AES (256), DES (56), 3DES (168), 
Blowfish (128), RC2 (128), RC4 (128), 
Twofish (256), CAST5 (128), CAST6 (256), Kahzad (128), Serpent (128), TEA (128), ZTEA (128), Camellia (256), DSA (4096), DH (8192), RSA (4096), 
ElGamal (4096), DHAES (4096), MD160, MD4, SHA2, RIPEMD160, Tiger, SHA1, HMAC-MD5 (512), 
HMAC-RIPEMD (160), HMAC-SHA1 (512), HMAC-SHA2 (512), MICHAEL MIA, Whirlpool
			 */
			if( setEncrytAlgoForEx3(encryptAlgo)) {
				return;
			}	
		}
	}
	
	private void setProductNo(String productNo) {
		// remove \r and \n in product no
		productNo = productNo.replaceAll("\r", "").replaceAll("\n", "");	
		ericProdNo = productNo.substring(0, productNo.length()-3).replaceAll(" ", "");;
		ericProdNo += " " + productNo.substring(productNo.length()-3);
/*
		if (productNo.matches(".*\\dR\\d{1}[A-Z]$")) {
			// if there is no space between product number and Rstate  
			//	Example: 4/CAX1053791R1A, 4/CAX1053791/2R1A, 4/CAX 105 3791/2R1A
			ericProdNo = productNo.substring(0, productNo.length()-3).replaceAll(" ", "");;
			ericProdNo = " " + productNo.substring(productNo.length()-3);
		} else {
			// if there is space between product number and Rstate  
			// Example: 13/CAX 105 4557 R1A, 4/CAX 105 3983/2 R1A			
			ericProdNo = productNo.substring(0, productNo.length()-3).replaceAll(" ", "");;
			ericProdNo = " " + productNo.substring(productNo.length()-3);
		}
*/		
	}

	/**
	 * Set a SVL column by its name
	 * 
	 * @param name
	 *            column name
	 * @param value
	 *            column value
	 */
	public void setColByName(String name, String value) {
		name = name.trim();
		value = value.trim();
		if (VENDOR_NAME.equals(name)) {
			vendorName = value;
		} else if (SW_NAME.equals(name)) {
			swName = value;
		} else if (SW_WEB_ADDRESS.equals(name)) {
			swWebAddr = value;
		} else if (SW_TYPE.equals(name)) {
			swType = value;
		} else if (SW_VERSION.equals(name)) {
			swVersion = value;
		} else if (ERICSSON_PRODUCT_NUMBER.equals(name)) {
			setProductNo(value);
		} else if (ERICSSON_3PP_LICENCE_PRODUCT_NUMBER.equals(name)) {
			eric3ppLicProdNo = value.replaceAll(" ", "");
		} else if (DESIGN_COUNTRY_OF_ORIGIN.equals(name)) {
			origCountry = value;
		} else if (EU_ECCN.equals(name)) {
			// if a cell value is numeric, POI API return a double number
			// but ECCN is an integer, so remove floating part if needed
			if (value.indexOf('.') != -1) {
				euEccn = value.substring(0, value.indexOf('.'));
			} else {
				euEccn = value;
			}
		} else if (US_ECCN.equals(name)) {
			// if a cell value is numeric, POI API return a double number
			// but ECCN is an integer, so remove floating part if needed
			if (value.indexOf('.') != -1) {
				usEccn = value.substring(0, value.indexOf('.'));
			} else {
				usEccn = value;
			}
		} else if (BIS_AUTHORIZATION.equals(name)) {
			bisAuth = value;
		} else if (ENCRYPTED_PROTOCOL.equals(name)) {
			protocol = value;
		} else if (ENCRYPT_ALGORITHM.equals(name)) {
//			encryptAlgo = value;
			setEncryptAlgo(value);
		} else {
			logger.error("Wrong column name: " + name);
		}

	}
	
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append(VENDOR_NAME + ":" + vendorName + "\n");
		buff.append(SW_NAME + ":" + swName + "\n");
		buff.append(SW_WEB_ADDRESS + ":" + swWebAddr + "\n");
		buff.append(SW_TYPE + ":" + swType + "\n");
		buff.append(SW_VERSION + ":" + swVersion + "\n");
		buff.append(ERICSSON_PRODUCT_NUMBER + ":" + ericProdNo + "\n");
		buff.append(ERICSSON_3PP_LICENCE_PRODUCT_NUMBER + ":" + eric3ppLicProdNo + "\n");
		buff.append(DESIGN_COUNTRY_OF_ORIGIN + ":" + origCountry + "\n");
		buff.append(EU_ECCN + ":" + euEccn + "\n");
		buff.append(US_ECCN + ":" + usEccn + "\n");
		buff.append(BIS_AUTHORIZATION + ":" + bisAuth + "\n");
		buff.append(ENCRYPTED_PROTOCOL + ":" + protocol + "\n");
		buff.append(ENCRYPT_ALGORITHM + ":" + encryptAlgo + "\n");
		buff.append("Symmetric:" + getSymmetric() + "\n");
		buff.append("Asymmetric:" + getAsymmetric() + "\n");
		buff.append("Hash:" + getHash() + "\n");
		buff.append("\n");
		return buff.toString();
	}
}