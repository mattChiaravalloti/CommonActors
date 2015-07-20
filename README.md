## CommonActors
##### Another practice app

#### This is another practice app--this time I only pushed the /app folder such that anyone viewing this repo will only see the relevant files.

### From the main activity, the user can enter two movie titles, hit the button, and then be presented with a new view that displays a list of actors common between the two movies.
1. URLContent is a wrapper class that establishes an httpconnection with a webpage and has the functionality to return that webpage's HTML as a list of strings.
2. URLParser is a specialized class that has the public static method to get a set of strings representing the collection of common actors between two movies.  It uses the Java Pattern class to compile regex's and check for matches on imdb movie listings.
3. ActorListStore is a singleton class that temporariy holds the set of common actors whenever the user enters 2 new movie titles on the home page.
4. ListActivity presents the list in an unstyled view and has a back button which returns to the main page.
5. MainActivity offers the user the chance to input the two movie titles and check their similarity.
