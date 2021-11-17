
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatching {
	public static void main(String args[]) throws IOException, FileNotFoundException, IllegalStateException {

		// String to be scanned to find the pattern.
		String line = " ";
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the word to search: ");
		String p1 = sc.nextLine();
		WebSearchEngine bfm = new WebSearchEngine();

		// Create a Pattern object

		Pattern r3 = Pattern.compile(p1);
		// Now create matcher object.
		Matcher m3 = r3.matcher(line);
		long _fileNumber = 0;
		try {
			File _directory = new File("src\\ConvertedTextFiles");
			File[] _fileArray = _directory.listFiles();
			for (long i = 0; i < 100; i++) {
				findData(_fileArray[(int) i], _fileNumber, m3, p1);

				_fileNumber++;
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		} finally {

		}
	}

	public static void findData(File _sourceFile, long fileNumber, Matcher _m3, String p1)
			throws FileNotFoundException, ArrayIndexOutOfBoundsException {
		WebSearchEngine bfm = new WebSearchEngine();
		bfm.searchWord(_sourceFile, p1);
		try {
			BufferedReader _rederObject = new BufferedReader(new FileReader(_sourceFile));
			String line = null;
			while ((line = _rederObject.readLine()) != null) {
				_m3.reset(line);
				while (_m3.find()) {

					System.out.println();
					System.out.println("Searched String : " + _m3.group());
					System.out.println("URL: " + _sourceFile.getName());

				}
			}
			_rederObject.close();

		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}
}
