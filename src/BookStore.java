import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Scanner;

public class BookStore {
    private final String name;
    private List<Novel> novels;

    // BookStore Constructor
    public BookStore(String name) {
        this.name = name;
        this.novels = new ArrayList<>();
        populateNovels(); // Populating the ArrayList with given data
    }

    // Method to populate the novels list
    private void populateNovels() {
        Scanner scanner = new Scanner(new File("books.csv"));
    }

    // Method to print all titles in UPPERCASE
    public void printAllTitles() {
        novels.forEach(novel -> System.out.println(novel.getTitle().toUpperCase()));
    }

    // Method to print all titles that contain the specified parameter
    public void printBookTitle(String title) {
        novels.stream()
                .filter(novel -> novel.getTitle().toLowerCase().contains(title.toLowerCase()))
                .forEach(novel -> System.out.println(novel.getTitle()));
    }

    // Method to print all titles in alphabetical order
    public void printTitlesInAlphaOrder() {
        novels.stream()
                .map(Novel::getTitle)
                .sorted()
                .forEach(System.out::println);
    }

    // Method to print all books from a specified decade
    public void printGroupByDecade(int decade) {
        int startYear = (decade / 10) * 10;
        novels.stream()
                .filter(novel -> novel.getYear() >= startYear && novel.getYear() < startYear + 10)
                .forEach(novel -> System.out.println(novel.getTitle()));
    }

    // Method to find the longest title
    public Novel getLongest() {
        return novels.stream()
                .max(Comparator.comparingInt(novel -> novel.getTitle().length()))
                .orElse(null);
    }

    // Method to check if there is a book written in a specified year
    public boolean isThereABookWrittenBetween(int year) {
        return novels.stream()
                .anyMatch(novel -> novel.getYear() == year);
    }

    // Method to count how many books contain a specific word in their title
    public long howManyBooksContain(String word) {
        return novels.stream()
                .filter(novel -> novel.getTitle().toLowerCase().contains(word.toLowerCase()))
                .count();
    }

    // Method to calculate the percentage of books written between two years
    public double whichPercentWrittenBetween(int first, int last) {
        long totalBooks = novels.size();
        long booksInRange = novels.stream()
                .filter(novel -> novel.getYear() >= first && novel.getYear() <= last)
                .count();
        return (double) booksInRange / totalBooks * 100;
    }

    // Method to find the oldest book
    public Novel getOldestBook() {
        return novels.stream()
                .min(Comparator.comparingInt(Novel::getYear))
                .orElse(null);
    }

    // Method to get books with a title of a specific length
    public List<Novel> getBooksThisLength(int titleLength) {
        return novels.stream()
                .filter(novel -> novel.getTitle().length() == titleLength)
                .collect(Collectors.toList());
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
