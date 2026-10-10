package com.oakwood.library.service;

import com.oakwood.library.event.ConsoleStoryNarrator;
import com.oakwood.library.event.LibraryEventListener;
import com.oakwood.library.fee.FeeCalculator;
import com.oakwood.library.fee.StandardFeeCalculator;
import com.oakwood.library.model.Book;
import com.oakwood.library.model.Patron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <h2>Library - The Coordinator</h2>
 * <p>
 * The <b>Library</b> class is the central coordinator of our application.
 * It manages the catalog of books, coordinates checkout and return transactions
 * between {@link Patron} and {@link Book}, and applies design patterns.
 * </p>
 *
 * <h3>Key Concepts for Students:</h3>
 * <ul>
 *   <li><b>Association &amp; Communication:</b> Objects communicate by passing
 *       references into methods (e.g., passing a {@code Patron} into {@code checkoutBook}).</li>
 *   <li><b>Strategy Pattern:</b> Late fees are computed by delegating to a {@link FeeCalculator}.
 *       The library doesn't calculate the fee arithmetic itself.</li>
 *   <li><b>Observer Pattern:</b> Every transaction notifies registered {@link LibraryEventListener}
 *       observers, keeping business rules cleanly decoupled from console output.</li>
 * </ul>
 *
 * @author Oakwood Reference Project
 * @version 1.0
 */
public class Library {

    private final List<Book> catalog;
    private final List<LibraryEventListener> listeners;
    private FeeCalculator feeCalculator;

    /**
     * Initializes a new Library with an empty catalog, a default {@link StandardFeeCalculator},
     * and a {@link ConsoleStoryNarrator} attached.
     */
    public Library() {
        this.catalog = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.feeCalculator = new StandardFeeCalculator();

        // Attach default console narrator to display events to students
        this.addListener(new ConsoleStoryNarrator());
    }

    /**
     * Registers a book into the library's catalog.
     *
     * @param book the book to register
     */
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Cannot add a null book to catalog.");
        }
        catalog.add(book);
        notifyBookAdded(book);
    }

    /**
     * Attempts to check out a book for a given patron.
     * <ol>
     *   <li>Verifies the patron has not exceeded their borrow limit (max 3 books).</li>
     *   <li>Searches the catalog for an available book matching the title.</li>
     *   <li>If found, mutates the book state to unavailable and hands the reference to the patron.</li>
     * </ol>
     *
     * @param title  title of the book requested
     * @param patron patron checking out the book
     * @return {@code true} if checkout succeeded; {@code false} otherwise
     */
    public boolean checkoutBook(String title, Patron patron) {
        if (title == null || patron == null) {
            throw new IllegalArgumentException("Title and patron must not be null.");
        }

        // Rule Check: Patron Borrow Limit (Feature 2)
        if (!patron.canBorrow()) {
            String reason = patron.getName() + " has reached the borrow limit of "
                    + Patron.MAX_BORROW_LIMIT + " books.";
            notifyCheckoutFailed(patron, title, reason);
            return false;
        }

        // Scan catalog for book
        for (Book book : catalog) {
            if (book.getTitle().equalsIgnoreCase(title.trim())) {
                if (book.isAvailable()) {
                    book.setAvailable(false);      // Mutate Book state
                    patron.receiveBook(book);      // Pass reference to Patron
                    notifyBookCheckedOut(patron, book);
                    return true;
                } else {
                    notifyCheckoutFailed(patron, title, "'" + title + "' is not available.");
                    return false;
                }
            }
        }

        notifyCheckoutFailed(patron, title, "'" + title + "' is not available.");
        return false;
    }

    /**
     * Returns a book assuming it was returned on time (0 late days).
     *
     * @param title  title of the book being returned
     * @param patron patron returning the book
     * @return {@code true} if return succeeded; {@code false} otherwise
     */
    public boolean returnBook(String title, Patron patron) {
        double fee = processReturn(title, patron, 0);
        return fee >= 0.0;
    }

    /**
     * Returns a book with loan duration tracking and late fee calculation (Feature 1 &amp; 3).
     *
     * @param title    title of the book
     * @param patron   patron returning the book
     * @param daysKept total days the patron possessed the book
     * @return late fee assessed in dollars (&gt;= 0.0), or -1.0 if return failed
     */
    public double returnBook(String title, Patron patron, int daysKept) {
        if (daysKept < 0) {
            throw new IllegalArgumentException("Days kept cannot be negative.");
        }
        return processReturn(title, patron, daysKept);
    }

    /**
     * Internal return coordinator that verifies catalog presence, patron ownership,
     * updates availability state, and computes late fees via the Strategy Pattern.
     */
    private double processReturn(String title, Patron patron, int daysKept) {
        if (title == null || patron == null) {
            throw new IllegalArgumentException("Title and patron must not be null.");
        }

        for (Book book : catalog) {
            if (book.getTitle().equalsIgnoreCase(title.trim()) && !book.isAvailable()) {
                if (patron.returnBook(book)) {
                    book.setAvailable(true); // Reset state
                    double lateFee = feeCalculator.calculateFee(daysKept, book.getDueDays());
                    notifyBookReturned(patron, book, lateFee);
                    return lateFee;
                } else {
                    notifyReturnFailed(patron, title, patron.getName() + " does not have this book.");
                    return -1.0;
                }
            }
        }

        notifyReturnFailed(patron, title, "Book '" + title + "' not found in library records.");
        return -1.0;
    }

    /**
     * Finds a book in the catalog by title (case-insensitive).
     *
     * @param title title to search for
     * @return Optional containing the book if present
     */
    public Optional<Book> findBook(String title) {
        if (title == null) return Optional.empty();
        return catalog.stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title.trim()))
                .findFirst();
    }

    /**
     * Sets the active late fee calculation policy (Strategy Pattern).
     *
     * @param feeCalculator the new fee calculation strategy
     */
    public void setFeeCalculator(FeeCalculator feeCalculator) {
        if (feeCalculator == null) {
            throw new IllegalArgumentException("Fee calculator strategy cannot be null.");
        }
        this.feeCalculator = feeCalculator;
    }

    /**
     * Gets the currently active late fee calculation strategy.
     *
     * @return active fee calculator strategy
     */
    public FeeCalculator getFeeCalculator() {
        return feeCalculator;
    }

    /**
     * Registers a new event listener (Observer Pattern).
     *
     * @param listener the listener to add
     */
    public void addListener(LibraryEventListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Unregisters an event listener.
     *
     * @param listener the listener to remove
     */
    public void removeListener(LibraryEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Returns an unmodifiable list of all books in the library catalog.
     *
     * @return catalog of books
     */
    public List<Book> getCatalog() {
        return Collections.unmodifiableList(catalog);
    }

    // --- Observer Notification Helpers ---
    private void notifyBookAdded(Book book) {
        for (LibraryEventListener listener : listeners) {
            listener.onBookAdded(book);
        }
    }

    private void notifyBookCheckedOut(Patron patron, Book book) {
        for (LibraryEventListener listener : listeners) {
            listener.onBookCheckedOut(patron, book);
        }
    }

    private void notifyCheckoutFailed(Patron patron, String title, String reason) {
        for (LibraryEventListener listener : listeners) {
            listener.onCheckoutFailed(patron, title, reason);
        }
    }

    private void notifyBookReturned(Patron patron, Book book, double lateFee) {
        for (LibraryEventListener listener : listeners) {
            listener.onBookReturned(patron, book, lateFee);
        }
    }

    private void notifyReturnFailed(Patron patron, String title, String reason) {
        for (LibraryEventListener listener : listeners) {
            listener.onReturnFailed(patron, title, reason);
        }
    }
}
