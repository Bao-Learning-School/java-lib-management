package com.oakwood.library.event;

import com.oakwood.library.model.Book;
import com.oakwood.library.model.Patron;

/**
 * Defines an Observer that receives notifications whenever library events occur.
 *
 * <p><b>Design Pattern: Observer Pattern (Behavioral)</b></p>
 *
 * <p>Why use the Observer Pattern here?
 * <ul>
 *   <li><b>Separation of Concerns:</b> Keeps business rules decoupled from console output.</li>
 *   <li><b>Loose Coupling:</b> The Library only broadcasts events without knowing UI details.</li>
 * </ul>
 *
 * @author Oakwood Reference Project
 * @version 1.0
 */
public interface LibraryEventListener {

  /**
   * Triggered when a new book is added to the library's catalog.
   *
   * @param book the book that was added
   */
  void onBookAdded(Book book);

  /**
   * Triggered when a patron successfully checks out a book.
   *
   * @param patron the patron who borrowed the book
   * @param book the book that was checked out
   */
  void onBookCheckedOut(Patron patron, Book book);

  /**
   * Triggered when a checkout attempt fails.
   *
   * @param patron the patron who attempted the checkout
   * @param title the title of the requested book
   * @param reason human-friendly explanation of why it failed
   */
  void onCheckoutFailed(Patron patron, String title, String reason);

  /**
   * Triggered when a patron successfully returns a book.
   *
   * @param patron the patron returning the book
   * @param book the book returned
   * @param lateFee the late fee assessed (0.0 if returned on time)
   */
  void onBookReturned(Patron patron, Book book, double lateFee);

  /**
   * Triggered when a book return attempt fails.
   *
   * @param patron the patron attempting the return
   * @param title the title of the book
   * @param reason explanation of why the return could not proceed
   */
  void onReturnFailed(Patron patron, String title, String reason);
}
