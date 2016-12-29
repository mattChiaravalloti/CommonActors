package com.mattchiaravalloti.commonactors;

import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IMDBParser {

    private static final String BASE_URL = "http://www.imdb.com";
    private static final String M_BASE_URL = "http://m.imdb.com";
    private static final String CAST_URL_EXTENSION = "/fullcredits?ref_=tt_cl_sm#cast";
    private static final String FILMOGRAPHY_ACTOR_EXTENSION = "/filmotype/actor";

    private static final Pattern ID_PATTERN = Pattern.compile("/(title|name)/(\\p{Alnum}{9})/.*");

    /**
     * Get the IMDB search URL for the argued IMDB entity name (i.e. actor name or movie title)
     * using a standard format for IMDB URLs
     *
     * @param entity The movie title or actor name for which to get the search URL
     * @return The formatted URL as a String
     */
    private static String getSearchURL(String entity) {
        String url = new StringBuilder(BASE_URL)
                .append("/find?q=")
                .append(entity
                        .replaceAll("\\s", "+")
                        .toLowerCase())
                .toString();
        return url;
    }

    /**
     * Get the IMDB ID for the argued IMDB entity name (i.e. actor name or movie title)
     *
     * @param entity The movie title or actor name for which to get the IMDB ID
     * @return Null if no ID can be found or an exception is encountered, otherwise the ID
     */
    private static String getIMDBID(String entity) {
        String url = getSearchURL(entity);

        String id = null;

        try {
            // get search page and parse into navigable doc
            Document doc = HttpConnection.connect(url).get();

            // if there was no result, return null
            Elements tables = doc.select("table.findList");
            if (tables == null || tables.isEmpty()) {
                return id;
            }

            // get first resutls table and then get the first result
            Element resultTable = tables.first();
            Element resultTitle = resultTable.select("td.result_text").first();

            // get the anchor tag to the result's movie page and parse the id
            String titleLink = resultTitle.children().first().attr("href");
            Matcher m = ID_PATTERN.matcher(titleLink);
            if (m.matches()) {
                id = m.group(2);
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
        String id = getIMDBID(title);

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
            Elements cast = castTable.select("td.itemprop");

            // include just the text in the spans
            for (Element actor : cast) {
                String name = actor.text();
                String path = actor.children().first().attr("href");
                actors.put(name, path);
            }

            return actors;
        } catch (IOException e) {
            return actors;
        }
    }

    private static Map<String,String> getMovieTitles(String actor) {
        String id = getIMDBID(actor);

        Map<String,String> titles = new HashMap<>();

        if (id == null) {
            return titles;
        }

        try {
            // build filmography url, connect to it, and parse it as a document
            String filmURL = new StringBuilder(M_BASE_URL)
                    .append("/name/")
                    .append(id)
                    .append(FILMOGRAPHY_ACTOR_EXTENSION)
                    .toString();

            Document doc = HttpConnection.connect(filmURL).get();

            Elements films = doc.select("div.col-md-6");

            for (Element film : films) {
                String name = film.select("span.h3").first().text();
                String path = film.children().first().attr("href");
                titles.put(name, path);
            }

            return titles;
        } catch (IOException e) {
            return titles;
        }
    }

    /**
     * Get (from IMDB) the common actors among all of the movies argued by title. At least one
     * movie must be included in the array of argued titles.
     *
     * @param titles The array of movie titles for which to find common actors
     * @return The Map of actors' names who are common among all argued movies mapped to a link to
     *          their IMDB path
     */
    public static Map<String,String> getCommonActors(String... titles) {
        // start with at least the actors from the first movie
        Map<String,String> commonActors = getActors(titles[0]);

        // for each of the remaining movies, retain only the one that are in common
        for (int i = 1; i < titles.length; i++) {
            commonActors.keySet().retainAll(getActors(titles[i]).keySet());
        }

        return commonActors;
    }

    /**
     * Get (from IMDB) the common movies among all of the actors argued by name. At least one actor
     * must be included in the array of argued names.
     *
     * @param actors The array of actornames for which to find common movies
     * @return The Map of movies' names that are common among all argued actors mapped to a link to
     *          their IMDB path
     */
    public static Map<String,String> getCommonMovies(String... actors) {
        Map<String,String> commonMovies = getMovieTitles(actors[0]);

        for (int i = 1; i < actors.length; i++) {
            commonMovies.keySet().retainAll(getMovieTitles(actors[i]).keySet());
        }

        return commonMovies;
    }
}
