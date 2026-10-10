package com.oakwood.library.event;

import com.oakwood.library.model.Book;
import com.oakwood.library.model.Patron;

/**
 * <h2>Design Pattern: Observer Pattern (Behavioral)</h2>
 * <p>
 * The <b>LibraryEventListener</b> interface defines an Observer that receives
 * notifications whenever significant events happen inside the library.
 * </p>
 *
 * <h3>Why use the Observer Pattern here?</h3>
 * <ul>
 *   <li><b>Separation of Concerns:</b> The {@code Library} coordinates business logic
 *       (checking out, returning, managing catalog), while the {@code Observer} handles
 *       how events are communicated (printing to console, saving to a file, updating a UI).</li>
 *   <li><b>Loose Coupling:</b> The Library doesn't know or care how listeners handle the event;
 *       it only broadcasts what happened.</li>
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
     * @param book   the book that was checked out
     */
    void onBookCheckedOut(Patron patron, Book book);

    /**
     * Triggered when a checkout attempt fails.
     *
     * @param patron the patron who attempted the checkout
     * @param title  the title of the requested book
     * @param reason human-friendly explanation of why it failed
     */
    void onCheckoutFailed(Patron patron, String title, String reason);

    /**
     * Triggered when a patron successfully returns a book.
     *
     * @param patron  the patron returning the book
     * @param book    the book returned
     * @param lateFee the late fee assessed (0.0 if returned on time)
     */
    void onBookReturned(Patron patron, Book book, double lateFee);

    /**
     * Triggered when a book return attempt fails.
     *
     * @param patron the patron attempting the return
     * @param title  the title of the book
     * @param reason explanation of why the return could not proceed
     */
    void onReturnFailed(Patron patron, String title, String reason);
}
