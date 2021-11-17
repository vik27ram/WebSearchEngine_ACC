import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Queue;
import java.util.*;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
	public static Queue<String> queue = new LinkedList<>();
	public static Set<String> marked = new HashSet<>();
	public static String regex = "http[s]*://(\\w+\\.)*(\\w+)";

	public static void bfsAlgorithm(String root) throws Exception {
		queue.add(root);
		BufferedReader br = null;

		while (!queue.isEmpty()) {
			String crawledUrl = queue.poll();
			System.out.println("\n Site Crawled :  " + crawledUrl + "===");

			// we limit to 1000 website here

			if (marked.size() > 100)
				return;

			boolean ok = false;
			URL url = null;

			while (!ok) {
				try {
					url = new URL(crawledUrl);
					br = new BufferedReader(new InputStreamReader(url.openStream()));
					ok = true;

				} catch (MalformedURLException e) {
					System.out.println("MalFormed URL: " + crawledUrl);
					crawledUrl = queue.poll();
					ok = false;

				} catch (IOException io) {
					System.out.println("IOEXception for URL: " + crawledUrl);
					crawledUrl = queue.poll();
					ok = false;

				}
			}

			StringBuilder sb = new StringBuilder();
			String tmp = null;

			while ((tmp = br.readLine()) != null) {
				sb.append(tmp);
			}

			tmp = sb.toString();
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(tmp);

			while (matcher.find()) {
				String w = matcher.group();

				if (!marked.contains(w)) {
					marked.add(w);
					System.out.println("Site added for crawling : " + w);
					queue.add(w);
				}
			}

			if (br != null) {
				br.close();
			}
		}
	}

	public static void showResults() {
		System.out.println("Website crawled : " + marked.size() + "\n");

		for (String s : marked) {
			System.out.println("* " + s);
		}
	}

	public static void main(String[] args) throws Exception {
		try {
			bfsAlgorithm("http://www1.uwindsor.ca/cs/");
			showResults();
		} catch (IOException e) {

		}
	}
}
