package com.oakwood.library.model;

import java.util.Objects;

/**
 * <h2>Book - The Data Carrier</h2>
 * <p>
 * In Object-Oriented Programming (OOP), a <b>Book</b> is a <i>Data Carrier</i>.
 * It models a real-world library book and keeps track of its own internal state.
 * </p>
 *
 * <h3>Key Concepts for Students:</h3>
 * <ul>
 *   <li><b>Encapsulation:</b> The fields ({@code title}, {@code author}, {@code isAvailable},
 *       {@code dueDays}) are {@code private}. Outside code cannot change them directly;
 *       it must call methods like {@link #setAvailable(boolean)}.</li>
 *   <li><b>State Mutation:</b> When someone borrows this book, its {@code isAvailable}
 *       flag changes from {@code true} to {@code false}. This is called <i>mutating</i> the state.</li>
 *   <li><b>Heap Allocation:</b> When you write {@code new Book("Dune", "Frank Herbert")},
 *       Java allocates memory on the <b>Heap</b> to store this unique book object.</li>
 * </ul>
 *
 * @author Oakwood Reference Project
 * @version 1.0
 */
public class Book {

    /** Default loan duration in days (2 weeks). */
    public static final int DEFAULT_DUE_DAYS = 14;

    private final String title;
    private final String author;
    private boolean isAvailable;
    private int dueDays;

    /**
     * Creates a new Book with the default loan period of 14 days.
     * Newly created books are always available by default.
     *
     * @param title  the title of the book (cannot be null or blank)
     * @param author the author who wrote the book (cannot be null or blank)
     */
    public Book(String title, String author) {
        this(title, author, DEFAULT_DUE_DAYS);
    }

    /**
     * Creates a new Book with a custom loan duration.
     *
     * @param title   the title of the book
     * @param author  the author of the book
     * @param dueDays how many days a patron can keep this book before it is overdue
     */
    public Book(String title, String author, int dueDays) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty.");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty.");
        }
        if (dueDays <= 0) {
            throw new IllegalArgumentException("Due days must be greater than 0.");
        }
        this.title = title.trim();
        this.author = author.trim();
        this.isAvailable = true;
        this.dueDays = dueDays;
    }

    /**
     * Gets the book's title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the book's author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Checks if the book is currently on the shelf and ready to borrow.
     *
     * @return {@code true} if available; {@code false} if already borrowed
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Updates the availability status of the book.
     * This is a prime example of <b>State Mutation</b>.
     *
     * @param available {@code true} when returned to shelf, {@code false} when checked out
     */
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    /**
     * Gets the allowed loan period in days.
     *
     * @return allowed loan days
     */
    public int getDueDays() {
        return dueDays;
    }

    /**
     * Sets a new allowed loan period in days.
     *
     * @param dueDays positive number of allowed loan days
     */
    public void setDueDays(int dueDays) {
        if (dueDays <= 0) {
            throw new IllegalArgumentException("Due days must be greater than 0.");
        }
        this.dueDays = dueDays;
    }

    /**
     * Returns a human-friendly string describing the book, matching the format:
     * {@code 'Title' by Author}.
     *
     * @return formatted string representation
     */
    @Override
    public String toString() {
        return "'" + title + "' by " + author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book other)) return false;
        return title.equalsIgnoreCase(other.title) && author.equalsIgnoreCase(other.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase(), author.toLowerCase());
    }
}
