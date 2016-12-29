package com.mattchiaravalloti.commonactors;

import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IMDBParser {

    private static final String BASE_URL = "http://www.imdb.com";
    private static final String CAST_URL_EXTENSION = "/fullcredits?ref_=tt_cl_sm#cast";

    private static final Pattern TITLE_PATTERN = Pattern.compile("/title/(\\p{Alnum}{9})/.*");

    /**
     * Get the IMDB search URL for the argued movie title using a standard format for IMDB URLs
     *
     * @param title The movie title for which to get the search URL
     * @return The formatted URL as a String
     */
    private static String getSearchURLForTitle(String title) {
        String url = new StringBuilder(BASE_URL)
                .append("/find?q=")
                .append(title
                        .replaceAll("\\s", "+")
                        .toLowerCase())
                .toString();
        return url;
    }

    /**
     * Get the IMDB ID for the argued movie title
     *
     * @param title The movie title for which to get the IMDB ID
     * @return Null if no ID can be found or an exception is encountered, otherwise the ID
     */
    private static String getTitleID(String title) {
        String url = getSearchURLForTitle(title);

        String id = null;

        try {
            // get search page and parse into navigable doc
            Document doc = HttpConnection.connect(url).get();

            // get first resutls table and then get the first result
            Element resultTable = doc.select("table.findList").first();
            Element resultTitle = resultTable.select("td.result_text").first();

            // get the anchor tag to the result's movie page and parse the id
            String titleLink = resultTitle.children().first().attr("href");
            Matcher m = TITLE_PATTERN.matcher(titleLink);
            if (m.matches()) {
                id = m.group(1);
            }

            return id;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Get the collection of actors (by name) who are in the argued movie
     *
     * @param title The movie for which to get the cast of actors
     * @return The set of actors who are in the argued movie. Empty if a problem is encountered.
     */
    private static Map<String,String> getActors(String title) {
        String id = getTitleID(title);

//        Set<String> actors = new HashSet<>();
        Map<String,String> actors = new HashMap<>();

        if (id == null) {
            return actors;
        }

        try {
            // build cast url, connect to it, and parse it as a document
            String castURL = new StringBuilder(BASE_URL)
                    .append("/title/")
                    .append(id)
                    .append(CAST_URL_EXTENSION)
                    .toString();

            Document doc = HttpConnection.connect(castURL).get();

            // get the table with all cast members and select just the spans with actors' names
            Element castTable = doc.select("table.cast_list").first();
//            Elements cast = castTable.select("span.itemprop");
            Elements cast = castTable.select("td.itemprop");

            // include just the text in the spans
            for (Element actor : cast) {
                String name = actor.text();
                String path = actor.children().first().attr("href");
//                actors.add(name);
                actors.put(name, path);
            }

            return actors;
        } catch (IOException e) {
            return actors;
        }
    }

    /**
     * Get (from IMDB) the common actors among all of the movies argued by title. At least one
     * movie must be included in the array of argued titles.
     *
     * @param titles The array of movie titles for which to find common actors
     * @return The Set of actors' names who are common among all argued movies
     */
    public static Map<String,String> getCommonActors(String... titles) {
        // start with at least the actors from the first movie
//        Set<String> commonActors = new HashSet<>(getActors(titles[0]));

        Map<String,String> commonActors = getActors(titles[0]);
        // for each of the remaining movies, retain only the one that are in common
        for (int i = 1; i < titles.length; i++) {
            commonActors.keySet().retainAll(getActors(titles[i]).keySet());
        }

        return commonActors;
    }
}
