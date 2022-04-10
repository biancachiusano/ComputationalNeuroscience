public class Testing {

		static char[] alphabet = new char[27];

	public static void main(String[] args) {
		prac2.init();

		Individual indi = new Individual("HHAAAAAAAAA".toCharArray());
		Individual indi1 = new Individual("BBBBBBBBBBB".toCharArray());

		Individual[] kids = prac2.newIndividuals(indi, indi1);

		System.out.println(kids[0]);
		System.out.println(kids[1]);



	}

}