package com.oakwood.library.factory;

import com.oakwood.library.model.Book;

/**
 * <h2>Design Pattern: Factory Pattern (Creational)</h2>
 * <p>
 * The <b>BookFactory</b> provides static factory methods to create common types
 * of books with pre-configured loan durations.
 * </p>
 *
 * <h3>Why use the Factory Pattern here?</h3>
 * <ul>
 *   <li><b>Readability:</b> Instead of remembering raw numbers like {@code new Book("Title", "Author", 7)},
 *       students can write {@code BookFactory.createBestseller("Title", "Author")}, which reads like natural English.</li>
 *   <li><b>Centralized Configuration:</b> If the library changes the loan period for
 *       bestsellers from 7 to 10 days, we only update this factory class.</li>
 * </ul>
 *
 * @author Oakwood Reference Project
 * @version 1.0
 */
public final class BookFactory {

    /** Standard loan duration for general reading material (14 days). */
    public static final int STANDARD_LOAN_DAYS = 14;
    /** High-demand bestseller loan duration (7 days). */
    public static final int BESTSELLER_LOAN_DAYS = 7;
    /** Non-circulating or high-demand reference material loan duration (3 days). */
    public static final int REFERENCE_LOAN_DAYS = 3;
    /** Extended loan duration for young readers and children's books (21 days). */
    public static final int CHILDREN_LOAN_DAYS = 21;

    private BookFactory() {
        // Utility class - prevent instantiation
    }

    /**
     * Creates a regular fiction or non-fiction book with a 14-day loan period.
     *
     * @param title  title of the book
     * @param author author of the book
     * @return newly instantiated Book
     */
    public static Book createStandardBook(String title, String author) {
        return new Book(title, author, STANDARD_LOAN_DAYS);
    }

    /**
     * Creates a popular bestseller with a shortened 7-day loan period.
     *
     * @param title  title of the bestseller
     * @param author author of the bestseller
     * @return newly instantiated Book
     */
    public static Book createBestseller(String title, String author) {
        return new Book(title, author, BESTSELLER_LOAN_DAYS);
    }

    /**
     * Creates a reference or study book with a short 3-day loan period.
     *
     * @param title  title of the reference book
     * @param author author of the reference book
     * @return newly instantiated Book
     */
    public static Book createReferenceBook(String title, String author) {
        return new Book(title, author, REFERENCE_LOAN_DAYS);
    }

    /**
     * Creates a children's storybook with an extended 21-day loan period.
     *
     * @param title  title of the children's book
     * @param author author of the children's book
     * @return newly instantiated Book
     */
    public static Book createChildrenBook(String title, String author) {
        return new Book(title, author, CHILDREN_LOAN_DAYS);
    }
}
