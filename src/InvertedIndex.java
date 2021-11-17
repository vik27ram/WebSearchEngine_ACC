
import java.util.LinkedList;
import java.util.Map;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class InvertedIndex implements Serializable {

	private static final boolean String = false;
	public static int currWordNumber;
	public static Tries root;
	public static HashMap<Integer, HashMap<String, Integer>> invertedIdxArray;

	public InvertedIndex() {
		root = new Tries(' ');
		invertedIdxArray = new HashMap<Integer, HashMap<java.lang.String, Integer>>();
		currWordNumber = 1;
	}

	// *************************************
	// update word occurrence in HashMap
	// *************************************
	public void updateWordOccurrence(int num, String url) {

		// if the doc is already present
		if (invertedIdxArray.get(num) != null) {

			// check if the url was also captured earlier
			if (invertedIdxArray.get(num).get(url) != null) {

				// update the occurrence of the word by 1
				invertedIdxArray.get(num).put(url, invertedIdxArray.get(num).get(url) + 1);
			} else {

				// word is found for the first time in this url
				invertedIdxArray.get(num).put(url, 1);
			}
		} else {

			// if word is captured for first time
			HashMap<String, Integer> urlMap = new HashMap<java.lang.String, Integer>();
			urlMap.put(url, 1);
			invertedIdxArray.put(num, urlMap);
		}
	}

	// *************************************
	// insert a word in the Trie
	// *************************************
	public void insertWord(String word, String url) {

		// if word found, update its occurrence
		int wordNum = search(word);

		if (wordNum != -1) {
			updateWordOccurrence(wordNum, url);
			return;
		}

		// If not found -- add new one
		Tries curr = root;
		for (char c : word.toCharArray()) {
			Tries child = curr.getChild(c);
			if (child != null) {
				curr = child;
			} else {
				curr.childNode.add(new Tries(c));
				curr = curr.getChild(c);
			}
			curr.count++;
		}

		// Update the invertedIndex list
		curr.isEnd = true;
		curr.wordNumber = currWordNumber;
		updateWordOccurrence(curr.wordNumber, url);
		currWordNumber++;
	}

	// *************************************
	// get all intertedIndexList
	// *************************************
	public void getAllInvertedIndexList() {

		System.out.println("Printing InvertedIndex List");
		for (Map.Entry<Integer, HashMap<String, Integer>> e : invertedIdxArray.entrySet()) {
			System.out.println(e);
		}
	}

	// **************************************************
	// find the word and if found return the wordNumber
	// **************************************************
	public int search(String word) {
		Tries curr = root;
		for (char c : word.toCharArray()) {
			if (curr.getChild(c) == null) {
				return -1;
			} else {
				curr = curr.getChild(c);
			}
		}
		if (curr.isEnd) {
			System.out.print(word + ": ");
			System.out.println(curr.wordNumber);
			return curr.wordNumber;
		}

		return -1;
	}

	// *************************************
	// removing the word from Trie
	// *************************************
	public void remove(String word, String url) {

		// check if the word is present
		int wordNum = search(word);
		if (wordNum == -1) {
			System.out.println("word not found");
			return;
		}

		// handling the invertedIndex
		invertedIdxArray.get(wordNum).remove(url);

		// handing the Trie
		Tries curr = root;
		for (char c : word.toCharArray()) {
			Tries child = curr.getChild(c);
			if (child.count == 1) {
				curr.childNode.remove();
				return;
			} else {
				child.count--;
				curr = child;
			}
		}
		curr.isEnd = false;
	}

	// *************************************
	// Find the distance between two words
	// Using the dynamic method describe
	// in the class
	// *************************************
	public int findEditDistance(String s1, String s2) {
		int distance[][] = new int[s1.length() + 1][s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			distance[i][0] = i;
		}
		for (int i = 0; i <= s2.length(); i++) {
			distance[0][i] = i;
		}
		for (int i = 1; i < s1.length(); i++) {
			for (int j = 1; j < s2.length(); j++) {
				if (s1.charAt(i) == s2.charAt(j)) {
					distance[i][j] = Math.min(Math.min((distance[i - 1][j]) + 1, (distance[i][j - 1]) + 1),
							(distance[i - 1][j - 1]));
				} else {
					distance[i][j] = Math.min(Math.min((distance[i - 1][j]) + 1, (distance[i][j - 1]) + 1),
							(distance[i - 1][j - 1]) + 1);
				}
			}
		}
		return distance[s1.length() - 1][s2.length() - 1];
	}

	// *****************************************
	// function to be exposed to load the data
	// *****************************************
	public void loadData(Collection<String> e, String url) {

		// process each element and pass it to the trie
		Iterator<String> itr = e.iterator();
		while (itr.hasNext()) {
			insertWord(itr.next(), url);
		}
	}

	// *************************************
	// guessing the word
	// *************************************
	public String[] guessWord(String prefix) {
		Tries curr = root;
		int wordLength = 0;
		String predictedWords[] = null;

		// get the count of number of words available
		for (int i = 0; i < prefix.length(); i++) {
			if (curr.getChild(prefix.charAt(i)) == null) {
				System.out.println("No suggestion");
				return null;
			} else if (i == (prefix.length() - 1)) {
				curr = curr.getChild(prefix.charAt(i));
				System.out.println("Char reading = " + prefix.charAt(i));
				System.out.println("Curr value =" + curr.data + "===Curr count= " + curr.count);
				wordLength = curr.count;
			} else {
				curr = curr.getChild(prefix.charAt(i));
			}
		}
		System.out.println("Number of words to be returned =" + wordLength);

		// preparing the output buffer
		predictedWords = new String[wordLength];
		for (int i = 0; i < predictedWords.length; i++) {
			predictedWords[i] = prefix;
		}

		// Temp array list to find all childs
		java.util.ArrayList<Tries> currentChildBuffer = new java.util.ArrayList<Tries>();
		java.util.ArrayList<Tries> nextChildBuffer = new java.util.ArrayList<Tries>();
		HashMap<Integer, String> wordCompleted = new HashMap<Integer, String>();

		// get the prefix child
		int counter = 0;
		if (curr.childNode != null) {
			for (Tries e : curr.childNode) {
				currentChildBuffer.add(e);
			}
		}

		// iterating all the children
		while (currentChildBuffer.size() != 0) {
			for (Tries e : currentChildBuffer) {

				// populate the string word
				while (wordCompleted.get(counter) != null) {
					counter++;
				}
				for (int j = 0; j < e.count; j++) {
					System.out.println(
							"e.data " + e.data + "========boolena" + e.isEnd + "=========e.counter " + e.count);

					// fixing to get the corrcet word
					if (e.isEnd && j == (e.count - 1)) {
						wordCompleted.put(counter, "done");
					}
					System.out.println("counter " + counter);
					predictedWords[counter] = predictedWords[counter] + e.data;
					counter++;
				}

				// iterating the child of each char
				for (Tries e1 : e.childNode) {
					nextChildBuffer.add(e1);
				}
			}

			// resetting the counter
			counter = 0;

			currentChildBuffer = new java.util.ArrayList<Tries>();
			if (nextChildBuffer.size() > 0) {
				currentChildBuffer = nextChildBuffer;
				nextChildBuffer = new java.util.ArrayList<Tries>();
			}
		}

		// output buffer
		for (String s : predictedWords) {
			System.out.println("Predicted Words =" + s);
		}

		return predictedWords;
	}

	// ************************************* ****************************
	// function to provide the most suitable word for the input word
	// This needs to be called only of the word is not found in the trie
	// ******************************************************************
	public String[] findCorrection(String word) {
		String suggestion[] = guessWord(word.substring(0, 1));
		ArrayList<String> correction = new ArrayList<String>();
		for (String s : suggestion) {
			if (findEditDistance(word, s) == 1) {
				correction.add(s);
			}
		}

		String suggestedWord[] = correction.toArray(new String[0]);
		System.out.println("*********correction*********");
		for (String s : suggestedWord) {
			System.out.println(s);
		}

		return suggestedWord;

	}

	// *****************************************
	// Main function to run the implementation
	// *****************************************
	public static void main(String[] arr) {
		InvertedIndex t = new InvertedIndex();
	}
}
