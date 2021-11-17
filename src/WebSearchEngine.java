import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebSearchEngine {

	static ArrayList<String> key = new ArrayList<String>();
	static Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();
	static Scanner sc = new Scanner(System.in);

	// return offset of first match or N if no match
	public static int search1(String pat, String txt) {
		int M = pat.length();
		int N = txt.length();

		for (int i = 0; i <= N - M; i++) {
			int j;
			for (j = 0; j < M; j++) {
				if (txt.charAt(i + j) != pat.charAt(j))
					break;
			}
			if (j == M)
				return i; // found at offset i
		}
		return N; // not found
	}

	// return offset of first match or N if no match
	public static int search2(String pat, String txt) {
		int M = pat.length();
		int N = txt.length();
		int i, j;
		for (i = 0, j = 0; i < N && j < M; i++) {
			if (txt.charAt(i) == pat.charAt(j))
				j++;
			else {
				i -= j;
				j = 0;
			}
		}
		if (j == M)
			return i - M; // found
		else
			return N; // not found
	}

	/***************************************************************************
	 * char[] array versions
	 ***************************************************************************/

	// return offset of first match or N if no match
	public static int search1(char[] pattern, char[] text) {
		int M = pattern.length;
		int N = text.length;

		for (int i = 0; i <= N - M; i++) {
			int j;
			for (j = 0; j < M; j++) {
				if (text[i + j] != pattern[j])
					break;
			}
			if (j == M)
				return i; // found at offset i
		}
		return N; // not found
	}

	// return offset of first match or N if no match
	public static int search2(char[] pattern, char[] text) {
		int M = pattern.length;
		int N = text.length;
		int i, j;
		for (i = 0, j = 0; i < N && j < M; i++) {
			if (text[i] == pattern[j])
				j++;
			else {
				i -= j;
				j = 0;
			}
		}

		if (j == M)
			return i - M; // found
		else
			return N; // not found
	}

	public int searchWord(File filePath, String p1) {
		int counter = 0;

		String data = "";
		try {
			BufferedReader _rederObject = new BufferedReader(new FileReader(filePath));
			String line = null;

			while ((line = _rederObject.readLine()) != null) {
				data = data + line;

			}
			_rederObject.close();

		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}


		String txt = data;

		int offset1a = 0;

		for (int loc = 0; loc <= txt.length(); loc += offset1a + p1.length()) {

			offset1a = search1(p1, txt.substring(loc));
			if ((offset1a + loc) < txt.length()) {
				counter++;
				System.out.println(p1 + " at position " + (offset1a + loc)); // printing pos of word
			}
		}
		if (counter != 0) {
			System.out.println("\nFound in " + filePath.getName());

		}
		return counter;
	}

	public static void sortValue(Hashtable<?, Integer> t, int occur) {

		// Transfer as List and sort it
		ArrayList<Map.Entry<?, Integer>> l = new ArrayList<Entry<?, Integer>>(t.entrySet());

		Collections.sort(l, new Comparator<Map.Entry<?, Integer>>() {

			public int compare(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		Collections.reverse(l);

		if (occur != 0) {
			System.out.println("\n------Page Ranking------\n");

			int n = 5;
			int j = 1;
			while (l.size() > j && n > 0) {
				System.out.println("(" + j + ") " + l.get(j) + " times ");
				j++;
				n--;
			}
		} else {

		}
		

	}

	public void didYouMean(String p1) {
		try {
			String line = " ";
			String pattern3 = "[a-zA-Z]+";
			

			// Create a Pattern object
			Pattern r3 = Pattern.compile(pattern3);
			// Now create matcher object.
			Matcher m3 = r3.matcher(line);
			int _fileNumber = 0;
			try {
				File _directory = new File("ConvertedTextFiles");
				File[] _fileArray = _directory.listFiles();
				for (int i = 0; i < 100; i++) {
					findData(_fileArray[i], _fileNumber, m3, p1);
					_fileNumber++;
				}

				Set keys = new HashSet();
				Integer value = 1;
				Integer val = 0;

				System.out.println("Did you mean? ");
				for (Map.Entry entry : numbers.entrySet()) {
					if (val == entry.getValue()) {
						break;

					} else {

						if (value == entry.getValue()) {
							// keys.add(entry.getKey()); //no break, looping entire hashtable
							System.out.print(entry.getKey() + "  ");

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

	public static void findData(File _sourceFile, int fileNumber, Matcher _m3, String p1)
			throws FileNotFoundException, ArrayIndexOutOfBoundsException {
		try {
			int i = 0;
			BufferedReader _rederObject = new BufferedReader(new FileReader(_sourceFile));
			String line = null;
			while ((line = _rederObject.readLine()) != null) {
				_m3.reset(line);
				while (_m3.find()) {
					key.add(_m3.group());
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

	public static void main(String[] args) {

		WebSearchEngine bfm = new WebSearchEngine();

		Hashtable<String, Integer> occurrenceTable = new Hashtable<String, Integer>();

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your search: ");
		String p1 = sc.nextLine();
		spellChecker spc = new spellChecker();
		spc.askDict(p1);
		long _fileNumber = 0;
		int occur = 0;
		int nn = 0;
		try {
			File _directory = new File("ConvertedTextFiles");

			File[] _fileArray = _directory.listFiles();
			for (int i = 0; i < 102; i++) {
				occur = bfm.searchWord(_fileArray[i], p1);

				occurrenceTable.put(_fileArray[i].getName(), occur);
				if (occur != 0) {
					nn++;
				}

				_fileNumber++;
			}

			if (nn == 0) {
				System.out.println("---------------------------------------------------");
				System.out.println("Searching in webpages.....");
				bfm.didYouMean(p1);

			}
			sortValue(occurrenceTable, nn);
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		} finally {

		}

	}

}
