
import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import java.util.Scanner;
import java.util.Set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class spellChecker {

	static ArrayList<String> key = new ArrayList<String>();
	static Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		spellChecker spc = new spellChecker();
		spc.askDict("");
	}

	public void askDict(String p1) {
		try {

			String line = " ";
			String pattern3 = "[a-zA-Z]+";

			Pattern r3 = Pattern.compile(pattern3);

			Matcher m3 = r3.matcher(line);

			findDictionary(m3, p1);

			Set keys = new HashSet();
			Integer value = 1;
			Integer val = 0;
			int counter = 0;
			Boolean k = true;
			for (Map.Entry en : numbers.entrySet()) {

				if (val == en.getValue()) {
					counter = 1;

					break;
				}
			}

			for (Map.Entry entry : numbers.entrySet()) {

				if (val == entry.getValue()) {
					break;

				} else {
					if (value == entry.getValue() && counter == 0) {
						while (k) {
							k = false;

							System.out.println("---------------------------------------------------");
							System.out.println("Checking for spelling errors.....");
							System.out.println("Sorry! Your word was not found in our Dictionary.");
							System.out.print("Are you looking for?  \n");
						}
						System.out.print(entry.getKey() + " \n");

					}

				}
			}

		} catch (Exception e) {

		}

	}

	public static void findDictionary(Matcher _m3, String p1)
			throws FileNotFoundException, ArrayIndexOutOfBoundsException {
		try {
			int i = 0;
			FileReader fr = new FileReader("Dictionary.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				_m3.reset(line);
				while (_m3.find()) {
					key.add(_m3.group());
				}
			}
			br.close();
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

		int[][] dp = new int[len1 + 1][len2 + 1];

		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}

		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}

		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);

				if (c1 == c2) {
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
