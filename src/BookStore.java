import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class BookStore {
    private final String name;
    private List<Novel> novels;
    private Map<String, Novel> novelMap;
    private Set<String> keySet;
    private List<String> keyList;

    // BookStore Constructor
    public BookStore(final String name) {
        this.name = name;
        this.novels = new ArrayList<>();
        populateNovels(); // Populating the ArrayList with given data
        this.keySet = novelMap.keySet();
        this.keyList = new ArrayList<>(keySet);
        Collections.sort(keyList);
    }

    // Method to populate the novels list
    private void populateNovels() {
        try (Scanner scanner = new Scanner(new File("C://Users//miste//IdeaProjects//Lab5//src//books.csv"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Using regex to handle quoted fields that might contain commas
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (data.length == 3) {
                    // Remove surrounding quotes from the title field, if present
                    String title = data[0].trim().replaceAll("^\"|\"$", "");
                    String author = data[1].trim();
                    int year = Integer.parseInt(data[2].trim());
                    novelMap.put(title, new Novel(title, author, year));

                    novels.add(new Novel(title, author, year));
                }
            }
        } catch (final FileNotFoundException e) {
            throw new RuntimeException("CSV file not found", e);
        }
    }

    // Method to print all titles in UPPERCASE
    public void printAllTitles() {
        for (Novel novel : novels){
            System.out.println(novel.getTitle().toUpperCase());
        }
    }

    // Method to print all titles that contain the specified parameter
    public void printBookTitle(final String filter) {
        for (Novel novel : novels){
            if (novel.getTitle().toLowerCase().contains(filter.toLowerCase())){
                System.out.println(novel.getTitle().toUpperCase());
            }
        }
    }

    // Method to print all titles in alphabetical order
    public void printTitlesInAlphaOrder() {
        final ArrayList<Novel> sortedNovels;

        sortedNovels = new ArrayList<>(novels);
        sortedNovels.sort(Comparator.comparing(Novel::getTitle));

        for (Novel novel : novels){
            System.out.println(novel.getTitle().toUpperCase());
        }
    }

    // Method to print all books from a specified decade
    public void printGroupByDecade(final int decade) {
        int startYear = (decade / 10) * 10;
        novels.stream()
                .filter(novel -> novel.getYear() >= startYear && novel.getYear() < startYear + 10)
                .forEach(novel -> System.out.println(novel.getTitle()));
    }

    // Method to find the longest title
    public Novel getLongest() {
        Novel longest = novels.getFirst();
        for (Novel novel : novels){
            if (novel.getTitle().length() > longest.getTitle().length()){
                longest = novel;
            }
        }
        return longest;
    }

    // Method to check if there is a book written in a specified year
    public boolean isThereABookWrittenBetween(final int year) {
        for (Novel novel : novels){
            if (novel.getYear() == year){
                return true;
            }
        }
        return false;
    }

    // Method to count how many books contain a specific word in their title
    public long howManyBooksContain(final String word) {
        long result;
        result = 0;

        for (Novel novel : novels){
            if (novel.getTitle().toLowerCase().contains(word.toLowerCase())){
                result++;
            }
        }
        return result;
    }

    // Method to calculate the percentage of books written between two years
    public double whichPercentWrittenBetween(final int first,final int last) {
        long totalBooks;
        long booksInRange;
        double percentWritten;

        totalBooks = novels.size();
        booksInRange = 0;

        for (Novel novel : novels){
            if (novel.getYear() >= first && novel.getYear() <= last){
                booksInRange ++;
            }
        }

        percentWritten = (double) booksInRange / totalBooks * 100;
        return percentWritten;
    }

    // Method to find the oldest book
    public Novel getOldestBook() {
        Novel oldest;
        ArrayList<Novel> sortedNovels;

        sortedNovels = new ArrayList<>(novels);
        sortedNovels.sort(Comparator.comparing(Novel::getYear));
        oldest = sortedNovels.get(0);

        return oldest;
    }

    // Method to get books with a title of a specific length
    public List<Novel> getBooksThisLength(final int titleLength) {
        ArrayList<Novel> filteredTitles = new ArrayList<>();

        for (Novel novel : novels){
            if (novel.getTitle().length() == titleLength){
                filteredTitles.add(novel);
            }
        }

        return filteredTitles;
    }

    // Main method to test the functionality
    public static void main(final String[] args) {
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
