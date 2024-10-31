package ca.bc.comp2522.lab06;

/**
 * Class represents novel
 * @author Alex He
 * @author Nickolay Makarenko
 * @version 1.0
 */
public class Novel{
    private static final int MIN_YEAR = 1000;
    private final String title;
    private final String author;
    private final int year;

    /**
     * Constructs a new {@code Novel} object with the specified title, author, and year.
     *
     * @param title  the title of the novel, must not be null or empty
     * @param author the author of the novel, must not be null or empty
     * @param year   the publication year of the novel, must be between {@code MIN_YEAR} and the current year
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Novel(final String title, final String author, final int year) 
    {
        validateTitle(title);
        validateAuthor(author);
        validateYear(year);
        this.title = title;
        this.author = author;
        this.year = year;
    }

    /**
     * Validates the title of the novel.
     *
     * @param title the title of the novel to validate
     * @return the validated title
     * @throws IllegalArgumentException if the title is null or empty
     */
    private String validateTitle(final String title) 
    {
        if (title == null ||
         title.trim().isEmpty())
         {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
        return title;
    }

    /**
     * Validates the author of the novel.
     *
     * @param author the author of the novel to validate
     * @return the validated author
     * @throws IllegalArgumentException if the author is null or empty
     */
    private String validateAuthor(final String author) 
    {
        if (author == null || author.trim().isEmpty())
        {
            throw new IllegalArgumentException("Author cannot be null or empty.");
        }
        return author;
    }

    /**
     * Validates the year of publication.
     *
     * @param year the year of publication to validate
     * @return the validated year
     * @throws IllegalArgumentException if the year is not between {@code MIN_YEAR} and the current year
     */
    private int validateYear(final int year) 
    {
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        if (year < MIN_YEAR ||
            year > currentYear) 
        {
            throw new IllegalArgumentException("Year must be between MIN_YEAR and " + currentYear + ".");
        }
        return year;
    }

    /**
     * Returns the title of the novel.
     *
     * @return the title of the novel
     */
    public String getTitle() 
    {
        return title;
    }

    /**
     * Returns the author of the novel.
     *
     * @return the author of the novel
     */
    public String getAuthor() 
    {
        return author;
    }

    /**
     * Returns the publication year of the novel.
     *
     * @return the publication year of the novel
     */
    public int getYear() 
    {
        return year;
    }
}

