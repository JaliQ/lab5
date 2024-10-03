public class Novel {
    private static final int MIN_YEAR = 1000;
    private final String title;
    private final String author;
    private final int year;

    public Novel(final String title,final String author, final int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    private String validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
        return title;
    }

    private String validateAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty.");
        }
        return author;
    }

    private int validateYear(int year) {
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        if (year < MIN_YEAR || year > currentYear) {
            throw new IllegalArgumentException("Year must be between MIN_YEAR and " + currentYear + ".");
        }
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }
}

