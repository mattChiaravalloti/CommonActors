package com.mattchiaravalloti.commonactors;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class URLContent {
	private URL url;
	private HttpURLConnection httpConnection;
	
	private List<String> htmlContents;
	
	public URLContent(String url) {
		try {
			this.url = new URL(url);

			this.httpConnection = (HttpURLConnection)this.url.openConnection();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		
		return this.htmlContents;
	}
}
