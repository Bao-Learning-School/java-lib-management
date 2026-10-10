package com.oakwood.library.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <h2>Patron - The Consumer</h2>
 * <p>
 * In our system, a <b>Patron</b> represents a library member who checks out books.
 * The Patron maintains an internal list of books they currently possess.
 * </p>
 *
 * <h3>Key Concepts for Students:</h3>
 * <ul>
 *   <li><b>Aggregation:</b> A Patron "has-a" list of {@link Book} objects.
 *       The Patron holds references to books, but does not "own" their existence—the
 *       books continue to exist even if the Patron object is garbage collected.</li>
 *   <li><b>Passing References:</b> When {@link #receiveBook(Book)} is called,
 *       Java does NOT copy the book. It passes an address (reference) pointing to the
 *       same Book instance sitting on the Heap.</li>
 *   <li><b>Borrow Limit (Business Rule):</b> A patron cannot borrow more than
 *       {@value #MAX_BORROW_LIMIT} books at one time.</li>
 * </ul>
 *
 * @author Oakwood Reference Project
 * @version 1.0
 */
public class Patron {

    /** Maximum number of books any patron may hold at the same time. */
    public static final int MAX_BORROW_LIMIT = 3;

    private final String name;
    private final List<Book> borrowedBooks;

    /**
     * Creates a new library Patron with an empty list of borrowed books.
     *
     * @param name the patron's name (cannot be null or blank)
     */
    public Patron(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Patron name cannot be null or empty.");
        }
        this.name = name.trim();
        this.borrowedBooks = new ArrayList<>();
    }

    /**
     * Gets the patron's name.
     *
     * @return the name of the patron
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if the patron is eligible to borrow another book.
     * Under the library rules, a patron cannot hold more than {@value #MAX_BORROW_LIMIT} books.
     *
     * @return {@code true} if current borrowed count &lt; {@value #MAX_BORROW_LIMIT}; {@code false} otherwise
     */
    public boolean canBorrow() {
        return borrowedBooks.size() < MAX_BORROW_LIMIT;
    }

    /**
     * Receives a book reference and appends it to the patron's borrowed list.
     *
     * @param book the book to borrow (must not be null)
     * @throws IllegalStateException    if the patron has already reached {@value #MAX_BORROW_LIMIT} books
     * @throws IllegalArgumentException if the book is null or already held by this patron
     */
    public void receiveBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Cannot receive a null book reference.");
        }
        if (!canBorrow()) {
            throw new IllegalStateException(name + " has reached the maximum borrowing limit of "
                    + MAX_BORROW_LIMIT + " books.");
        }
        if (borrowedBooks.contains(book)) {
            throw new IllegalArgumentException(name + " already holds a reference to " + book);
        }
        borrowedBooks.add(book);
    }

    /**
     * Relinquishes a borrowed book from the patron's possession.
     *
     * @param book the book to return
     * @return {@code true} if the book was held and successfully removed; {@code false} otherwise
     */
    public boolean returnBook(Book book) {
        return borrowedBooks.remove(book);
    }

    /**
     * Checks whether the patron is currently holding a specific book.
     *
     * @param book the book to check
     * @return {@code true} if held, {@code false} otherwise
     */
    public boolean hasBook(Book book) {
        return borrowedBooks.contains(book);
    }

    /**
     * Returns an unmodifiable view of the currently borrowed books.
     * This protects the patron's internal collection from being altered directly from outside.
     *
     * @return unmodifiable list of borrowed books
     */
    public List<Book> getBorrowedBooks() {
        return Collections.unmodifiableList(borrowedBooks);
    }

    /**
     * Returns how many books the patron currently holds.
     *
     * @return count of borrowed books
     */
    public int getBorrowCount() {
        return borrowedBooks.size();
    }

    /**
     * Displays all books currently in the patron's possession to standard output.
     */
    public void showBorrowedBooks() {
        System.out.println(name + "'s Borrowed Books: " + borrowedBooks);
    }

    @Override
    public String toString() {
        return "Patron{" + "name='" + name + '\'' + ", borrowedCount=" + borrowedBooks.size() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patron patron)) return false;
        return name.equalsIgnoreCase(patron.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}
