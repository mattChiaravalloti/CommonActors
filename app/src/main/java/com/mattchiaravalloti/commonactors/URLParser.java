package com.mattchiaravalloti.commonactors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class URLParser {
	
//	private static final String BASE_URL = "http://www.imdb.com";
//	private static final String CAST_URL_EXTENSION = "/fullcredits?ref_=tt_cl_sm#cast";
//
//	private static final String SEARCH_RESULT_ID_PATTERN =
//			"<tr class=\"findResult odd\"> <td class=\"primary_photo\"> "
//			+ "<a href=\"/title/(\\p{Alnum}{9})/?.*";
//
//	private static final String CAST_LIST_NAME_PATTERN =
//			".*<span class=\"itemprop\" itemprop=\"name\">(.*)</span>";
//
//	private static Pattern SEARCH_RESULT_ID_COMPILED_PATTERN = Pattern.compile(SEARCH_RESULT_ID_PATTERN);
//
//	private static Pattern CAST_LIST_NAME_COMPILED_PATTERN = Pattern.compile(CAST_LIST_NAME_PATTERN);
//
//	private static String getTitleURL(String title) {
//		int spaceIndex = title.indexOf(' ');
//
//		List<String> titleWords = new ArrayList<String>();
//
//		while (spaceIndex != -1) {
//			String word = title.substring(0, spaceIndex).toLowerCase();
//			titleWords.add(word);
//			title = title.substring(spaceIndex+1);
//			spaceIndex = title.indexOf(' ');
//		}
//
//		String lastWord = title.substring(0).toLowerCase();
//		titleWords.add(lastWord);
//
//		String searchURL = BASE_URL + "/find?q=";
//		for (String word : titleWords) {
//			searchURL += word + "+";
//		}
//
//		// get rid of trailing '+'
//		searchURL = searchURL.substring(0, searchURL.length() - 1);
//
//		title = title.replaceAll("\\s", "+").toLowerCase();
//
//		StringBuilder url = new StringBuilder(BASE_URL).append("/find?q=").append(title);
//
//		return url.toString();
//	}
//
//	private static String getTitleID(String title) {
//		String searchURL = getTitleURL(title);
//
//		URLContent searchPage = new URLContent(searchURL);
//		List<String> searchContent = searchPage.getHTMLContent();
//
//		String titleID = "";
//
//		for (String line : searchContent) {
//			Matcher m = p.matcher(line);
//			if (m.matches()) {
//				titleID = m.group(1);
//				break;
//			}
//		}
//
//		return titleID;
//	}
//
//	private static Set<String> getListOfActors(String title) {
//		String titleID = getTitleID(title);
//
//		String castURL = new StringBuilder(BASE_URL)
//				.append("/title/")
//				.append(titleID)
//				.append(CAST_URL_EXTENSION)
//				.toString();
//
//		URLContent castPage = new URLContent(castURL);
//		List<String> castContent = castPage.getHTMLContent();
//
//		Set<String> actorNames = new HashSet<String>();
//
//		for (String line : castContent) {
//			Matcher m = CAST_LIST_NAME_COMPILED_PATTERN.matcher(line);
//			if (m.matches()) {
//				actorNames.add(m.group(1));
//			}
//		}
//
//		return actorNames;
//	}
//
//	public static Set<String> getCommonActors(String title1, String title2) {
//		Set<String> movie1 = getListOfActors(title1);
//		Set<String> movie2 = getListOfActors(title2);
//
//		Set<String> common = new HashSet<String>(movie1);
//
//		common.retainAll(movie2);
//
//		return common;
//	}
	
}
