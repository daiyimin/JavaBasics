package com.ericsson.tc;

public class Test {
	
	public static void testPattern1(String s) {
/*		if (s.matches("\\d+/CAX[0-9 ]+[/0-9]*\\dR\\d{1}[A-Z]$")) {
			System.out.println("match");
		} else {
			System.out.println("not match");

		}
*/
		if (s.matches(".*\\dR\\d{1}[A-Z]$")) {
			System.out.println("match");
		} else {
			System.out.println("not match");

		}
	
	}

	public static void testPattern2(String s) {
		if (s.matches("\\d+/CAX[0-9 ]+R\\d{1}[A-Z]$")) {
			System.out.println("match");
		} else {
			System.out.println("not match");

		}
	}
	
	public static void testPattern3(String s) {
		if (s.matches("\\d+/CAX[0-9 ]+[/0-9]+ ?R\\d{1}[A-Z]$")) {
			System.out.println("match");
		} else {
			System.out.println("not match");

		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("1");
		String s ="4/CAX1053791R1A";
		testPattern1(s);
		testPattern2(s);
		testPattern3(s);

		System.out.println("2");
		s ="4/CAX1053791/2R1A";
		testPattern1(s);
		testPattern2(s);
		testPattern3(s);

		System.out.println("3");
		s ="4/CAX 105 3791/2R1A";
		testPattern1(s);
		testPattern2(s);
		testPattern3(s);

		
		System.out.println("4");
		s = "13/CAX 105 4557 R1A"; 
		testPattern1(s);
		testPattern2(s);
		testPattern3(s);

		System.out.println("5");
		s = "4/CAX 105 3983/2 R1A";
		testPattern1(s);
		testPattern2(s);
		testPattern3(s);
	}

}
