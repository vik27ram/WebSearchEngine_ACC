
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class word_prediction2 {

	static ArrayList<String> key = new ArrayList<String>();
	static Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();
	static Scanner sc = new Scanner(System.in);
	static String p1 = sc.nextLine();

	public static void main(String[] args) {
		word_prediction2 ww = new word_prediction2();
		ww.didYouMean();

	}

	public void didYouMean() {
		try {

			// String to be scanned to find the pattern.
			String line = " ";
			String pattern3 = "[a-zA-Z]+";
			
			// Create a Pattern object
			Pattern r3 = Pattern.compile(pattern3);
			// Now create matcher object.
			Matcher m3 = r3.matcher(line);
			int _fileNumber = 0;
			try {
				File _directory = new File("src\\ConvertedTextFiles");
				File[] _fileArray = _directory.listFiles();
				for (int i = 0; i < 100; i++) {
					findData(_fileArray[i], _fileNumber, m3);
					_fileNumber++;
				}
				
				Set keys = new HashSet();
				Integer value = 1;
				Integer val = 0;
				for (Map.Entry entry : numbers.entrySet()) {
					if (val == entry.getValue()) {
						// System.out.println("found");
						break;

					} else {

						if (value == entry.getValue()) {
							// keys.add(entry.getKey()); //no break, looping entire hashtable
							System.out.print(entry.getKey() + ", ");

						}

					}
				}

			} catch (Exception e) {
				System.out.println("Exception:" + e);
			} finally {

			}

		} catch (Exception e) {

		}
	}

	public static void findData(File _sourceFile, int fileNumber, Matcher _m3)
			throws FileNotFoundException, ArrayIndexOutOfBoundsException {
		try {
			int i = 0;
			BufferedReader _rederObject = new BufferedReader(new FileReader(_sourceFile));
			String line = null;
//    		System.out.println("URL Contain in File "+_sourceFile.getName());
			while ((line = _rederObject.readLine()) != null) {
				_m3.reset(line);
				while (_m3.find()) {
					key.add(_m3.group());
//                 System.out.println("Founded URL : "+_m3.group() + "\n");
				}
			}
			_rederObject.close();
			for (int p = 0; p < key.size(); p++) {
				numbers.put(key.get(p), editDistance(p1.toLowerCase(), key.get(p).toLowerCase()));
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}

	}

	public static int editDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();

		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];

		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}

		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}

		// iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);

				// if last two chars equal
				if (c1 == c2) {
					// update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;

					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}
		return dp[len1][len2];
	}
}
