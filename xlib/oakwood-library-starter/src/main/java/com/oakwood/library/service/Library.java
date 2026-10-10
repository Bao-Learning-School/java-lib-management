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
 * Coordinates library inventory, checkout/return transactions, and events.
 *
 * <p>Key Concepts for Students:
 * <ul>
 *   <li><b>Association:</b> Collaborates with {@link Patron} and {@link Book} via methods.</li>
 *   <li><b>Strategy Pattern:</b> Delegates fine calculation to {@link FeeCalculator}.</li>
 *   <li><b>Observer Pattern:</b> Emits notifications to {@link LibraryEventListener}.</li>
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
   * Initializes a new Library with an empty catalog, standard fee policy, and console narrator.
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
    // TODO: Task 6.1 - Implement addBook:
    // 1. If book is null, throw new IllegalArgumentException("Cannot add a null book to catalog.");
    // 2. Add book to 'catalog' list.
    // 3. Notify listeners using notifyBookAdded(book);
  }

  /**
   * Attempts to check out a book for a given patron.
   *
   * @param title title of the book requested
   * @param patron patron checking out the book
   * @return {@code true} if checkout succeeded; {@code false} otherwise
   */
  public boolean checkoutBook(String title, Patron patron) {
    if (title == null || patron == null) {
      throw new IllegalArgumentException("Title and patron must not be null.");
    }

    // TODO: Task 6.2 - Implement checkoutBook:
    // Step A (Rule Check): Check if patron.canBorrow() is false!
    //      If false:
    //          String reason = patron.getName() + " has reached the borrow limit of "
    //                  + Patron.MAX_BORROW_LIMIT + " books.";
    //          notifyCheckoutFailed(patron, title, reason);
    //          return false;
    //
    // Step B (Catalog Search): Loop through 'catalog':
    //      If book.getTitle().equalsIgnoreCase(title.trim()):
    //          If book.isAvailable():
    //              - Mutate book state: book.setAvailable(false);
    //              - Pass reference: patron.receiveBook(book);
    //              - Notify observers: notifyBookCheckedOut(patron, book);
    //              - return true;
    //          Else (book is already borrowed by someone else!):
    //              - notifyCheckoutFailed(patron, title, "'" + title + "' is not available.");
    //              - return false;
    //
    // Step C: If no book with this title was found in catalog:
    //      - notifyCheckoutFailed(patron, title, "'" + title + "' is not available.");
    //      - return false;

    return false; // Replace this line with your code
  }

  /**
   * Returns a book assuming it was returned on time (0 late days).
   *
   * @param title title of the book being returned
   * @param patron patron returning the book
   * @return {@code true} if return succeeded; {@code false} otherwise
   */
  public boolean returnBook(String title, Patron patron) {
    // TODO: Task 6.3 - Delegate to returnBook(title, patron, 0):
    // If fee >= 0.0, return true; otherwise return false.
    return false; // Replace this line with your code
  }

  /**
   * Returns a book with loan duration tracking and late fee calculation.
   *
   * @param title title of the book
   * @param patron patron returning the book
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
   * Internal return coordinator that verifies catalog, resets state, and computes late fees.
   */
  private double processReturn(String title, Patron patron, int daysKept) {
    if (title == null || patron == null) {
      throw new IllegalArgumentException("Title and patron must not be null.");
    }

    // TODO: Task 6.4 - Implement processReturn:
    // Step A: Loop through 'catalog':
    //      If book.getTitle().equalsIgnoreCase(title.trim()) && !book.isAvailable():
    //          If patron.returnBook(book):
    //              - Reset state: book.setAvailable(true);
    //              - Calculate late fee using strategy:
    //                  double lateFee = feeCalculator.calculateFee(daysKept, book.getDueDays());
    //              - Notify observers: notifyBookReturned(patron, book, lateFee);
    //              - return lateFee;
    //          Else (patron does not have this book):
    //              - notifyReturnFailed(
    //                    patron, title, patron.getName() + " does not have this book.");
    //              - return -1.0;
    //
    // Step B: If book is not found in catalog (or wasn't checked out):
    //      - notifyReturnFailed(
    //            patron, title, "Book '" + title + "' not found in library records.");
    //      - return -1.0;

    return -1.0; // Replace this line with your code
  }

  /**
   * Finds a book in the catalog by title (case-insensitive).
   *
   * @param title title to search for
   * @return Optional containing the book if present
   */
  public Optional<Book> findBook(String title) {
    if (title == null) {
      return Optional.empty();
    }
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
