package com.ericsson.tc.doc2400;

import java.util.Map;
import java.util.HashMap;

public class EncryptAlgorithm {
	// Symmetric Algorithm
	public static final String[] SYMMETRIC_ALGORITHM = { "EEA0", "128-EEA1",
			"128-EEA2", "128-EEA3", "2DES", "3DES ", "3-Way", "A4", "A4KEA",
			"A5/1", "A5/2", "A5/3", "A5/4", "AES", "AES-CCM", "Anubis",
			"Arcfour", "ARC4", "Baton", "BISS-E", "Blowfish", "C2", "Camellia",
			"CAST", "CAST3", "CAST5 ", "CAST6", "ChaCha", "CRYPT", "DEA",
			"DES", "DESede", "Enigma", "DESX", "f8", "FCrypt ", "FEAL",
			"Fortuna", "Fortezza", "GEA1", "GEA2", "GEA3", "GEA4", "GOST",
			"Grain128", "Grainv1", "HC128", "HC256", "ICE", "ICE-n", "IDEA",
			"ISAAC", "Juniper", "Kasumi", "Khafre", "Khazad", "Khufu", "LEX",
			"LOKI 91", "MARS", "MISTY1", "Matrix", "MULTI2", "NLS", "Nokeon",
			"Panama", "PBE, PBES1", "PBES2", "QUAD", "RC2", "RC4", "RCiv",
			"RC4-HMAC", "RC5", "RC6", "Rabbit", "Rijndael", "ROT13", "SAFER",
			"SAFER+", "Salsa20", "Sapphire", "SEAL", "SEED", "Serpent",
			"SHACAL", "SHARK", "Skipjack", "SMS4", "SNOW", "SNOW", "SNOW 2.0",
			"SNOW3G", "Sober", "SPEED", "Square", "TCRYPT", "TDES", "TEA",
			"tnepres", "Trivium", "Turing", "Twofish", "UEA1 ", "UEA2", "VMPC",
			"WAKE", "XETA", "XTEA", "Yarrow", "ZUC", "2-key 3DES", "CAST128",
			"CAST256", "Kahzad", "ZTEA" };

	// Hash Algorithm
	public static final String[] HASH_ALGORITHM = { "CRC16", "CRC32", "Crush",
			"Crypt", "DCC, DCC2", "fastHash", "Fletcher", "FNV-1", "GHASH",
			"GOST", "HAVAL", "LM Hash", "MD160", "MD2", "MD3", "MD4", "MD5",
			"MD6", "MDC2", "MSCash", "MSCash2", "NT Hash", "NTLM", "RIPEMD",
			"RIPEMD128", "RIPEMD160", "RIPEMD256", "RIPEMD320", "SHA", "SHA1",
			"SHA2", "Skein", "Snefru", "SSHA", "Tiger", "Whirlpool" };

	public static final String[] INTEGRITY_ALGORITHM = { "Badger", "CBC-MAC",
			"CMAC", "DAA", "EIA0", "128-EIA1", "128-EIA2", "128-EIA3", "f9",
			"GMAC", "GOST3411", "HMAC", "Michael", "OMAC", "PBMAC1", "PMAC",
			"Poly1305-AES", "PRF", "RMAC", "UIA1", "UIA2", "UMAC", "VMPC-MAC ",
			"XCBC", "Michael MIC", "Michael MIA" };

	public static final String[] INTEGRITY_HMAC_VARIANTS = { "HMAC-MD2 ",
			"HMAC-MD4", "HMAC-MD5", "HMAC-RipeMD", "HMAC-SHA", "HMAC-SHA1",
			"HMAC-SHA2", "HMAC-SHA224 ", "HMAC-SHA256", "HMAC-SHA384",
			"HMAC-SHA512", "HMAC-Skein", "HMAC-Tiger", "HMAC-Whirlpool" };

	// Asymmetric Algorithm
	public static final String[] ASYMMETRIC_ALGORITHM = { "ACE",
			"Blum-Goldwasser", "DH ", "DH_anon", "DHE", "DHES", "DHAES",
			"DHIES", "ECDH", "ECDH_anon", "ECDHE", "ElGamal", "KEA", "LUC",
			"McEliece", "MQV", "EC-MQV", "NTRUEncrypt", "Rabin", "RSA",
			"RSAES-OAEP", "RSAES-PKCS1-v1_5", "XTR" };

	public static final String[] SIGNATURE_ALGORITHM = { "DSA", "DSS", "ECDSA",
			"ECGOST", "ElGamal", "GOST3410-94", "GOST3410-2001", "MIA", "NR",
			"EC-NR", "NTRUSign", "Rabin", "RSA", "RSASSA-PSS",
			"RSASSA-PKCS1-v1_5", "Schnorr" };

	// Synonym
	public static final Map<String, String> SYNONYM = new HashMap<String, String>() {
		{
			// Symmetric synonym
			put("DESede", "3DES");
			put("2-key 3DES", "3DES");
			put("TDES", "3DES");
			put("Arcfour", "RC4");
			put("ARC4", "RC4");
			put("RCiv", "RC4");
			put("A5/4", "UEA1");
			put("CAST5", "CAST128");
			put("CAST6", "CAST256");
			// put("crypt(1)", "CRYPT");
			put("DES", "DEA");
			put("Kahzad", "Khazad"); // add for typo in SVL
			put("ZTEA", "XTEA");

			// hash synonym
			put("Michael MIC", "Michael");
			put("Michael MIA", "Michael");

			// Asymmetric synonym
			put("DHES", "DHIES");
			put("DHAES", "DHIES");
		}
	};

	public static final boolean isSymmetric(String encryptAlgoName) {
		boolean found = false;
		for (String symmetric : SYMMETRIC_ALGORITHM) {
			if (encryptAlgoName.contains(symmetric)) {
				found = true;
				break;
			}
		}
		return found;
	}

	public static final boolean isAsymmetric(String encryptAlgoName) {
		boolean found = false;
		for (String asymmetric : ASYMMETRIC_ALGORITHM) {
			if (encryptAlgoName.contains(asymmetric)) {
				found = true;
				break;
			}
		}
		for (String asymmetric : SIGNATURE_ALGORITHM) {
			if (encryptAlgoName.contains(asymmetric)) {
				found = true;
				break;
			}
		}
		return found;
	}

	public static final boolean isHash(String encryptAlgoName) {
		for (String hash : HASH_ALGORITHM) {
			if (encryptAlgoName.contains(hash)) {
				return true;
			}
		}
		for (String hash : INTEGRITY_ALGORITHM) {
			if (encryptAlgoName.contains(hash)) {
				return true;
			}
		}
		for (String hash : INTEGRITY_HMAC_VARIANTS) {
			if (encryptAlgoName.contains(hash)) {
				return true;
			}
		}
		return false;
	}

	public static final boolean isEqual(String left, String right) {
		if (left == null && right == null) {
			return true;
		} else if (left == null || right == null) {
			return false;
		} else {
			if (left.equals(right)) {
				return true;
			}
			if (left.equals(SYNONYM.get(right))) {
				return true;
			}
			if (right.equals(SYNONYM.get(left))) {
				return true;
			}
			return false;
		}
	}
}
