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

	public void setEncryptAlgo(String encryptAlgo) {
		// parse the SVL encrypt algorithm into three part
		if (encryptAlgo != null && encryptAlgo.length() > 0) {			
			encryptAlgo = encryptAlgo.replaceAll(":", "");
			
			// Normally, each encrypt part start with Symmetric, Asymmetric, Hash
			// Use them as delimiter to parse encrypt info
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
			}
			if (asymmetricIdxInSVL != -1) {
				if (hasIdxInSVL != -1) {
					asymmetricInSVL = encryptAlgo.substring(asymmetricIdxInSVL
							+ SVL3PPBean.ASYMMETRIC.length(), hasIdxInSVL);
				} else {
					asymmetricInSVL = encryptAlgo.substring(asymmetricIdxInSVL
							+ SVL3PPBean.ASYMMETRIC.length());
				}
			}
			if (hasIdxInSVL != -1) {
				hashInSVL = encryptAlgo.substring(hasIdxInSVL + SVL3PPBean.HASH.length());
			}
			
			// If no delimitor is found, and encrypt info is not empty, then we have to use encrypt algorithm name to find Symmetric, Asymmetric, Hash part
			if (symmetricIdxInSVL == -1 && asymmetricIdxInSVL == -1 && hasIdxInSVL == -1) {
				// if there is nonsense content in the encrypt column, have to skip it
				if (encryptAlgo.equals("TBD")) {
					return;
				}
				
				encryptAlgo = encryptAlgo.replaceAll("\r", "").replaceAll("\n", ",");
				String[] encryptAlgoArr = encryptAlgo.split(","); 
				
				int flag = 0; // 0 - symmetric, 1 - asymmetric, 2 - hash, 3 quit
				StringBuffer sb = new StringBuffer();
				for(String algo: encryptAlgoArr) {
					algo = algo.trim();
					if(algo.isEmpty()) {
						continue;
					}
					switch (flag) {
					case 0:
						if(EncryptAlgorithm.isSymmetric(algo)) {
							sb.append(algo).append(",");
							continue;
						} else {
							sb.deleteCharAt(sb.length()-1); // remove last ","
							symmetricInSVL = sb.toString();
							sb = new StringBuffer();
							flag = 1;
						}
					case 1:
						if(EncryptAlgorithm.isAsymmetric(algo)) {
							sb.append(algo).append(",");
							continue;
						} else {
							sb.deleteCharAt(sb.length()-1);
							asymmetricInSVL = sb.toString();
							sb = new StringBuffer();
							flag = 2;
						}
					case 2:
						if(EncryptAlgorithm.isHash(algo)) {
							sb.append(algo).append(",");
							continue;
						} else {
							sb.deleteCharAt(sb.length()-1);
							hashInSVL = sb.toString();
							flag = 3;
						}
					}
					if (flag == 3) {
						break;
					}
				}
			}
		}
		
		this.encryptAlgo = encryptAlgo;
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
			// remove \r and \n in product no
			value = value.replaceAll("\r", "").replaceAll("\n", "");			
			// remove space in product no
			int idx = value.lastIndexOf(" ");
			ericProdNo = value.substring(0, idx).replaceAll(" ", "");
			ericProdNo += value.substring(idx);
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