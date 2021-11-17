
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class expt {
	public static void main(String args[]) {
		String s1 = "Web Services Architecture.txt=339";
		String org1 = ".+?txt";
		String org = "\\=(.*)$";
		Pattern r_org = Pattern.compile(org1, Pattern.CASE_INSENSITIVE);
		Pattern r_org1 = Pattern.compile(org, Pattern.CASE_INSENSITIVE);
		Matcher m2 = r_org.matcher(s1);
		Matcher m3 = r_org1.matcher(s1);
		while (m2.find()) {
			while (m3.find()) {
				System.out.print("Top 10 documents");
				System.out.print("No of Occurences ");
				System.out.println();
				System.out.print(m2.group(0));
				System.out.println("                   " + m3.group(0).substring(1));
			}
		}
	}
}