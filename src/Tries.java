
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

//Class to implement Trie
@SuppressWarnings("serial")
class Tries implements Serializable {
	char data;
	int count;
	boolean isEnd;
	int wordNumber;
	LinkedList<Tries> childNode;

	// Constructor
	public Tries(char n) {
		data = n;
		count = 0;
		isEnd = false;
		wordNumber = -1;
		childNode = new LinkedList<Tries>();
	}

	// getChar
	public Tries getChild(char c) {
		if (childNode != null) {
			for (Tries child : childNode) {
				if (child.data == c) {
					return child;
				}
			}
		}
		return null;
	}
}