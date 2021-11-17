
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.text.Document;

import org.jsoup.*;

public class HTMLTextConvertor {

	public static void main(String[] args) throws IOException, FileNotFoundException, NullPointerException {

		int _fileNumber = 1;
		try {
			File _directory = new File("src\\W3C Web Pages");
			File[] _fileArray = _directory.listFiles();
			File[] _fileArray2 = new File[102];
			int _numberOfFiles = 0;
			for (int _i = 0; _i < (_fileArray.length); _i++) {
				if (_fileArray[_i].isFile()) {
					_fileArray2[_numberOfFiles] = _fileArray[_i];
					_numberOfFiles++;
				}
			}

			for (int i = 0; i < 100; i++) {
				convertHtmlToText(_fileArray2[i], _fileNumber);
				_fileNumber = _fileNumber + 1;
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		} finally {

		}
	}

	public static void convertHtmlToText(File _resourceFile, int _fileNumber)
			throws IOException, FileNotFoundException, NullPointerException {
		try {
			org.jsoup.nodes.Document _doc = Jsoup.parse(_resourceFile, "UTF-8");
			BufferedWriter out = new BufferedWriter(
					new FileWriter("ConvertedTextFiles\\" + _resourceFile.getName() + ".txt"));
			out.write(_doc.text());
			out.close();
			System.out.println("File " + _resourceFile.getName() + " converted into " + _resourceFile.getName()
					+ ".txt successfully");

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
