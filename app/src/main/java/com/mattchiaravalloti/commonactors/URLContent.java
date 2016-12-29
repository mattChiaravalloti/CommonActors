package com.mattchiaravalloti.commonactors;

import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLContent {

	private static final Pattern TITLE_PATTERN = Pattern.compile("/title/(\\p{Alnum}{9})/.*");

	private URL url;
	private HttpURLConnection httpConnection;
	
	private List<String> htmlContents;

	private Document doc;

	private boolean encounteredException;
	
	public URLContent(String url) {
		try {
			this.doc = HttpConnection.connect(url).get();

//			this.url = new URL(url);
//
//			this.httpConnection = (HttpURLConnection)this.url.openConnection();

			this.encounteredException = false;
		} catch (IOException e) {
			encounteredException = true;
		}
	}

	public static String getTitleId(String url) {
		String id = null;

		try {
			// get search page and parse into navigable doc
			Document doc = HttpConnection.connect(url).get();

			// get first resutls table and then get the first result
			Element resultTable = doc.select("table.findList").first();
			Element resultTitle = resultTable.select("td.result_text").first();

			// get the anchor tag to the result's movie page and parse the id
			String titleLink = resultTitle.select("a").attr("href");
			Matcher m = TITLE_PATTERN.matcher(titleLink);
			if (m.matches()) {
				id = m.group(1);
			}

			return null;
		} catch (IOException e) {
			return null;
		}
	}



	public List<String> getHTMLContent() {
		if (this.htmlContents != null) {
			return this.htmlContents;
		}

		this.htmlContents = new ArrayList<String>();

		Scanner in = null;
		try {
			in = new Scanner(httpConnection.getInputStream());
			while (in.hasNextLine()) {
				this.htmlContents.add(in.nextLine());
			}
		} catch (IOException e) {
			this.encounteredException = true;
		} finally {
			if (in != null) {
				in.close();
			}
		}

		return this.htmlContents;
	}

	public boolean isSuccessful() {
		return !this.encounteredException;
	}
}
