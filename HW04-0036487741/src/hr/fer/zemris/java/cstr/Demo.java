package hr.fer.zemris.java.cstr;

public class Demo {

	public static void main(String[] args) {

		char[] govno = { 'g', 'o', 'v', 'n', 'o' };

		CString cs = new CString(govno);

		System.out.println(cs);

		govno[0] = 'a';
		// achieved the immutability
		System.out.println(cs);
	}
}
