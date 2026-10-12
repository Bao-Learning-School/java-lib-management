package com.oakwood.library.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.oakwood.library.model.Book;
import com.oakwood.library.event.LibraryEventListener;
/**
 * Models a library member who can borrow books up to a fixed limit.
 *
 * <p>Key Concepts for Students:
 * <ul>
 *   <li><b>Aggregation:</b> Patron holds a collection of {@link Book} references.</li>
 *   <li><b>Passing References:</b> Java passes pointers to objects on the Heap.</li>
 *   <li><b>Borrow Limit:</b> Invariant rule preventing more than 3 books per patron.</li>
 * </ul>
 *
 * @author Oakwood Reference Project
 * @version 1.0
 */
public class Patron implements LibraryEventListener {

  /** Maximum number of books any patron may hold at the same time. */
  public static final int MAX_BORROW_LIMIT = 3;

  private final String name;
  private final List<Book> borrowedBooks;

  /**
   * Creates a new library Patron with an empty list of borrowed books.
   *
   * @param name the patron's name
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
   *
   * @return {@code true} if current borrowed count &lt; 3; {@code false} otherwise
   */
  public boolean canBorrow() {
    return borrowedBooks.size() < MAX_BORROW_LIMIT; 
  }

  /**
   * Receives a book reference and appends it to the patron's borrowed list.
   *
   * @param book the book to borrow (must not be null)
   * @throws IllegalStateException if patron reached borrow limit
   * @throws IllegalArgumentException if book is null or duplicate
   */
  public void receiveBook(Book book) {
    if(book = null) {
      throw new IllegalArgumentException("book name cannot be null");
    } else if(!canBorrow()) {
      throw new IllegalStateException("patron has too many books");
    } else if (borrowedBooks.contains(book)) {
      throw new IllegalArgumentException("patron already has this book");
    } else {
      borrowedBooks.add(book);
    }
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
  public void onBookAdded(Book book) {
    System.out.println(name + " now knows that " + book.getTitle() + " is added.");
  }

  @Override 
  public void onBookCheckedOut(Patron patron, Book book) {
  }

  @Override 
  public void onCheckoutFailed(Patron patron, String title, String reason) {
  }

  @Override 
  public void onBookReturned(Patron patron, String title, double lateFee) {
  }

  @Override 
  public void onReturnFailed(Patron patron, String title, String reason) {
  }

  @Override
  public String toString() {
    return "Patron{" + "name='" + name + '\'' + ", borrowedCount=" + borrowedBooks.size() + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Patron patron)) {
      return false;
    }
    return name.equalsIgnoreCase(patron.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name.toLowerCase());
  }
}
