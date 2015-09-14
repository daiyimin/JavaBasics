package com.ericsson.tc;

public class CheckNewAlgorithm {

	public static void main(String[] args) {
		String[] algoIn2400 = { "128-EEA1 (128)", "128-EEA2 (128)",
				"3-Way (96)", "2DES (112)", "3DES (168)", "AES/Rijndael (256)",
				"Anubis (320)", "Baton (320)", "Blowfish (448)",
				"Camellia (256)", "CAST5 (128)", "CAST6 (256)", "CMEA(64)",
				"DEAL(256)", "DES (56)", "DESX (184)", "FCrypt (56)",
				"GOST(256)", "IDEA (128)", "ISAAC(8192)", "Kasumi (128)",
				"Khazad (128)", "LEX (256)", "LOKI91(64)", "Lucifer(128)",
				"MARS(256)", "MISTY1(128)", "MMB(128)", "NewDES(120)",
				"NLS (64)", "NOEKEON(128)", "ORYX(96)", "RC2 (1024)",
				"RC4 (4096)", "RC5 (2048)", "RC6 (256)", "SAFER(128)",
				"Salsa20(256)", "SEAL(160)", "SEED (128)", "Serpent (256)",
				"Skipjack (80)", "Sober(128)", "SPEED (256)", "Square(128)",
				"TEA (128)", "tnepres (256)", "Twofish (256)", "UEA1 (128) ",
				"UEA2 (128)", "WAKE(256)", "XTEA (128)", "ACE(2048)",
				"DH (8192)", "DHAES(4098)", "DSA (4096)", "ECDH (571)",
				"ECDHE(571)", "ECDSA (571)", "ElGamal (4096)", "RSA (65536)",
				"RSA (4096)", "128-EIA1 (128)", "128-EIA2 (128)",
				"AES-GCM(256)", "AES-XCBC-MAC (128)", "ECC (512)", "FASTHASH",
				"Fletcher", "Haval", "HMAC", "HMAC-MD5 (512)",
				"HMAC- RIPEMD(160)", "HMAC-SHA (512)", "HMAC-SHA1 (512)",
				"HMAC-SHA2(1024)", "MD160", "MD2", "MD4", "MD5", "MDC2",
				"Michaels MIC", "RIPEMD", "RIPEMD160", "SHA (512)", "SHA1",
				"SHA2", "Snefru", "Tiger", "UIA1 (128)", "UIA2 (128)",
				"Whirlpool", "HMAC-MD5-96 (128)", "HMAC-SHA-1-96 (128)" };

		String algoUsedBySshd = "RC2(1024),3DES(168),AES(256),Camellia(256),CAST6(256),SEED(128),DES(56),"
				+ "RC5(2040),Twofish(256),Blowfish(448),VMPC(128),XTEA(128),RC4(2048),NOEKEON(128),Skipjack(80),"
				+ "Salsa20(256),RC6(256),PBE(56),RSA(4096),DH(8192),ElGamal(4096),GOST(256),MQV(512),DSA(4096),"
				+ "ECNR(571),ECDSA(571),ECDH(571),ECMQV(571),SHA,RipeMD160,SHA2,MD4,SHA1,RIPEMD,MD5,MD2,Whirlpool";
		String[] sshd = algoUsedBySshd.split(",");
		
		for (String s: sshd) {
			boolean found = false;
			int count = 0;
			s = s.replaceAll(" ", "");
			for (String curS: algoIn2400) {
				curS = curS.replaceAll(" ", "");
				if (curS.equalsIgnoreCase(s)) {
					found = true;
//					System.out.println("OK, " + s + " is already in 2400");
					break;
				}
			}
			if (!found) {
				count ++;
				System.out.println("NOK, " + s + " is not in 2400");
			}
		}
	
	}

}
