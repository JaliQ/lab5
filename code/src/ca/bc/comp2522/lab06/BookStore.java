package ca.bc.comp2522.lab06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BookStore 
{
    private final String name;
    private List<Novel> novels;
    private Map<String, Novel> novelMap;
    private Set<String> keySet;
    private List<String> keyList;


    private final int TITLE_INDEX           = 0;
    private final int AUTHOR_INDEX          = 1;
    private final int YEAR_INDEX            = 2;
    private final int VALID_LIST_LENGTH     = 3;
    private final int YEARS_IN_DECADE       = 10;
    private final int PERCENT_MULTIPLIER    = 100;
    /**
     * BookStore Constructor
     * @param name name of the book store as a String
     */
    public BookStore(final String name) 
    {   
        this.name = name;
        this.novels = new ArrayList<>();

        populateNovels(); // Populating the ArrayList with given data

        this.keySet = novelMap.keySet();
        this.keyList = new ArrayList<>(keySet);

        Collections.sort(keyList);
    }

    /**
     * Method to populate the novels list
     */
    private void populateNovels()
     {
        novelMap = new HashMap<String, Novel>();
        try (Scanner scanner = new Scanner(new File("src/books.csv"))) 
        {
            while (scanner.hasNextLine()) 
            {
                String line = scanner.nextLine();
                // Using regex to handle quoted fields that might contain commas
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (data.length == VALID_LIST_LENGTH)
                {
                    // Remove surrounding quotes from the title field, if present
                    String title = data[TITLE_INDEX].trim().replaceAll("^\"|\"$", "");
                    String author = data[AUTHOR_INDEX].trim();
                    int year = Integer.parseInt(data[YEAR_INDEX].trim());
                    novelMap.put(title, new Novel(title, author, year));

                    novels.add(new Novel(title, author, year));
                }
            }
        } catch (final FileNotFoundException e) 
        {
            throw new RuntimeException("CSV file not found", e);
        }
    }

    /**
     * Method to print all titles in UPPERCASE.
     */
    public void printAllTitles()
    {
        for (Novel novel : novels)
        {
            System.out.println(novel.getTitle().toUpperCase());
        }
    }

    /**
     * Method to print all titles that contain the specified parameter.
     * @param filter word to filter by as a string
     */
    public void printBookTitle(final String filter) 
    {
        for (Novel novel : novels){
            if (novel.getTitle().toLowerCase().contains(filter.toLowerCase()))
            {
                System.out.println(novel.getTitle().toUpperCase());
            }
        }
    }

    /**
     * Method to print all titles in alphabetical order.
     */
    public void printTitlesInAlphaOrder() 
    {
        final ArrayList<Novel> sortedNovels;

        sortedNovels = new ArrayList<>(novels);
        sortedNovels.sort(Comparator.comparing(Novel::getTitle));

        for (Novel novel : novels){
            System.out.println(novel.getTitle().toUpperCase());
        }
    }

    /**
     * Method to print all book sfrom a specified decade.
     * @param decade decade to search from as an int
     */
    public void printGroupByDecade(final int decade) 
    {
        int startYear = (decade / YEARS_IN_DECADE) * YEARS_IN_DECADE;
        novels.stream()
                .filter(novel -> novel.getYear() >= startYear &&
                                 novel.getYear() < startYear + 10)
                .forEach(novel -> System.out.println(novel.getTitle()));
    }

    /**
     * Method to find the longest title.
     * @return the longest title as a Novel
     */
    public Novel getLongest() 
    {
        Novel longest = novels.getFirst();

        for (Novel novel : novels)
        {
            if (novel.getTitle().length() > longest.getTitle().length())
            {
                longest = novel;
            }
        }
        return longest;
    }

    /**
     * Method to check if there is a book wirtten in specified year.
     * @param year year to filter by as an int
     * @return true if there is a book in specified year false otherwise
     */
    public boolean isThereABookWrittenBetween(final int year) 
    {
        for (Novel novel : novels)
        {
            if (novel.getYear() == year)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to count how many books contain a specific word in their title.
     * @param word word to check for ammount of books as a string
     * @return ammount of books with specified title as a long.
     */
    public long howManyBooksContain(final String word)
    {
        long result;
        result = 0;

        for (Novel novel : novels)
        {
            if (novel.getTitle().toLowerCase().contains(word.toLowerCase()))
            {
                result++;
            }
        }
        return result;
    }

    /**
     * method to calculate the percentage of books that are written between two years.
     * @param first start year as an int.
     * @param last end year as an int
     * @return percentage of books that are between the parameters
     */
    public double whichPercentWrittenBetween(final int first,final int last) 
    {
        long totalBooks;
        long booksInRange;
        double percentWritten;

        totalBooks = novels.size();
        booksInRange = 0;

        for (Novel novel : novels){
            if (novel.getYear() >= first && 
                novel.getYear() <= last)
            {
                booksInRange ++;
            }
        }

        percentWritten = (double) booksInRange / totalBooks * PERCENT_MULTIPLIER;
        return percentWritten;
    }

    /**
     * Method to find the oldest book inside of the book store
     * @return oldest book as a Novel
     */
    public Novel getOldestBook()
    {
        Novel oldest;
        ArrayList<Novel> sortedNovels;

        sortedNovels = new ArrayList<>(novels);
        sortedNovels.sort(Comparator.comparing(Novel::getYear));
        oldest = sortedNovels.get(TITLE_INDEX);

        return oldest;
    }

    // Method to get books with a title of a specific length
    /**
     * Method to get book(s) with a title of specified length
     * @param titleLength length of title as an int
     * @return a List of novels as an ArrayList
     */
    public List<Novel> getBooksThisLength(final int titleLength) 
    {
        ArrayList<Novel> filteredTitles = new ArrayList<>();

        for (Novel novel : novels)
        {
            if (novel.getTitle().length() == titleLength)
            {
                filteredTitles.add(novel);
            }
        }

        return filteredTitles;
    }

    /**
     * Testing function.
     * @param args arguments
     */
    public static void main(final String[] args) 
    {
        final BookStore bookstore;
        final Novel oldest;
        final List<Novel> fifteenCharTitles;

        bookstore = new BookStore("Classic Novels Collection");

        System.out.println("All Titles in UPPERCASE:");
        bookstore.printAllTitles();

        System.out.println("\nBook Titles Containing 'the':");
        bookstore.printBookTitle("the");

        System.out.println("\nAll Titles in Alphabetical Order:");
        bookstore.printTitlesInAlphaOrder();

        System.out.println("\nBooks from the 2000s:");
        bookstore.printGroupByDecade(2000);

        System.out.println("\nLongest Book Title:");
        Novel longest = bookstore.getLongest();
        if (longest != null) {
            System.out.println(longest.getTitle());
        }

        System.out.println("\nIs there a book written in 1950?");
        System.out.println(bookstore.isThereABookWrittenBetween(1950));

        System.out.println("\nHow many books contain 'heart'?");
        System.out.println(bookstore.howManyBooksContain("heart"));

        System.out.println("\nPercentage of books written between 1940 and 1950:");
        System.out.println(bookstore.whichPercentWrittenBetween(1940, 1950) + "%");

        System.out.println("\nOldest book:");
        oldest = bookstore.getOldestBook();
        if (oldest != null) {
            System.out.println(oldest.getTitle() + " by " + oldest.getAuthor() + ", " + oldest.getYear());
        }

        System.out.println("\nBooks with titles 15 characters long:");
        fifteenCharTitles = bookstore.getBooksThisLength(15);
        fifteenCharTitles.forEach(novel -> System.out.println(novel.getTitle()));
    }
}
