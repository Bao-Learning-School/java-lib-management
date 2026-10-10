package com.oakwood.library.event;

import com.oakwood.library.model.Book;
import com.oakwood.library.model.Patron;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("ConsoleStoryNarrator Observer Tests")
class ConsoleStoryNarratorTest {

    @Test
    @DisplayName("Should handle all library event notifications smoothly")
    void testNarratorEventHandling() {
        ConsoleStoryNarrator narrator = new ConsoleStoryNarrator();
        Book book = new Book("Effective Java", "Joshua Bloch");
        Patron patron = new Patron("Alex");

        assertDoesNotThrow(() -> narrator.onBookAdded(book));
        assertDoesNotThrow(() -> narrator.onBookCheckedOut(patron, book));
        assertDoesNotThrow(() -> narrator.onCheckoutFailed(patron, "Dune", "Book is not available."));
        assertDoesNotThrow(() -> narrator.onBookReturned(patron, book, 0.0));
        assertDoesNotThrow(() -> narrator.onBookReturned(patron, book, 2.50));
        assertDoesNotThrow(() -> narrator.onReturnFailed(patron, "Dune", "Alex does not have this book."));
    }
}
