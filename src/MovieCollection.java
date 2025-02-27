import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private ArrayList<String> cast;
    private ArrayList<String> genres;
    private ArrayList<Double> highestRated;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
        cast = makeCast();
        genres = makeGenres();
        highestRated = makeHighestRated();
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        switch (option) {
            case "t" -> searchTitles();
            case "c" -> searchCast();
            case "k" -> searchKeywords();
            case "g" -> listGenres();
            case "r" -> listHighestRated();
            case "h" -> listHighestRevenue();
            default -> System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<>();

        // search through ALL movies in collection
        for (Movie movie : movies) {
            String movieTitle = movie.getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.contains(searchTerm)) {
                //add the Movie object to the results list
                results.add(movie);
            }
        }
        sortResults(results);

        displayMovie(results);
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        // display them all to the user
        for (int i = 0; i < cast.size(); i++)
        {
            String word = cast.get(i);

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + word);
        }
        System.out.println("\nWhich actor would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();

        ArrayList<Movie> results = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getCast().contains(cast.get(choice - 1))) {
                results.add(movie);
            }
        }
        sortResults(results);

        displayMovie(results);
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        searchTerm = searchTerm.toLowerCase();

        ArrayList<Movie> results = new ArrayList<>();

        // search through ALL movies in collection
        for (Movie movie : movies) {
            String keyword = movie.getKeywords();
            keyword = keyword.toLowerCase();

            if (keyword.contains(searchTerm)) {
                //add the Movie object to the results list
                results.add(movie);
            }
        }
        sortResults(results);

        displayMovie(results);
    }

    private void listGenres()
    {
        for (int i = 0; i < genres.size(); i++)
        {
            String word = genres.get(i);

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + word);
        }
        System.out.println("\nWhich genre would you like see movies from?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();

        ArrayList<Movie> results = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getGenres().contains(genres.get(choice - 1))) {
                results.add(movie);
            }
        }
        sortResults(results);

        displayMovie(results);
    }

    private void listHighestRated()
    {
        for (int i = 0; i < 50; i++) {
            double word = highestRated.get(i);

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + word);
        }
    }

    private void listHighestRevenue()
    {
        System.out.println();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }

    private ArrayList<String> makeCast() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Movie movie : movies) {
            String[] actors = movie.getCast().split("\\|");
            for (String actor : actors) {
                if (!arrayList.contains(actor)) {
                    arrayList.add(actor);
                }
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private ArrayList<String> makeGenres() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Movie movie : movies) {
            String[] genre = movie.getGenres().split("\\|");
            for (String g : genre) {
                if (!arrayList.contains(g)) {
                    arrayList.add(g);
                }
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private ArrayList<Double> makeHighestRated() {
        ArrayList<Double> userRating = new ArrayList<>();
        for (Movie movie : movies) {
            userRating.add(movie.getUserRating());
        }
        userRating.sort(Collections.reverseOrder());
        return userRating;
    }

    private void displayMovie(ArrayList<Movie> results) {
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }
}