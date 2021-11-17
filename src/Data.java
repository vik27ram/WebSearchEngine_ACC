import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Data {

	public static String[] rec() throws FileNotFoundException {
		// TODO Auto-generated method stub
		String[] a = new String[14382];
		Scanner s;
		s = new Scanner(new File("ConvertedTextFiles\\WordBank.txt"));
		int count = 0;
		while (s.hasNext()) {
			a[count++] = s.next();
		}
		s.close();
		return a;
	}

	@SuppressWarnings("resource")
	public static String[] main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String[] a = new String[14382];
		Scanner s;
		s = new Scanner(new File("ConvertedTextFiles\\WordBank.txt"));
		int count = 0;
		while (s.hasNext()) {

			a[count++] = s.next();
		}

		return a;
	}

}
