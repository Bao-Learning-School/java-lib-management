package com.oakwood.library.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Book Class Tests (Data Carrier & State Mutation)")
class BookTest {

    @Test
    @DisplayName("Should initialize book with default 14 due days and available status")
    void testDefaultInitialization() {
        Book book = new Book("The Hobbit", "J.R.R. Tolkien");

        assertEquals("The Hobbit", book.getTitle());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
        assertTrue(book.isAvailable(), "New book should be available on shelf by default");
        assertEquals(Book.DEFAULT_DUE_DAYS, book.getDueDays());
        assertEquals("'The Hobbit' by J.R.R. Tolkien", book.toString());
    }

    @Test
    @DisplayName("Should initialize book with custom due days")
    void testCustomDueDaysInitialization() {
        Book book = new Book("Dune", "Frank Herbert", 7);

        assertEquals(7, book.getDueDays());
        assertTrue(book.isAvailable());
    }

    @Test
    @DisplayName("Should mutate availability state correctly")
    void testStateMutation() {
        Book book = new Book("1984", "George Orwell");

        // Simulate checkout
        book.setAvailable(false);
        assertFalse(book.isAvailable(), "Book state should mutate to unavailable upon checkout");

        // Simulate return
        book.setAvailable(true);
        assertTrue(book.isAvailable(), "Book state should mutate to available upon return");
    }

    @Test
    @DisplayName("Should reject invalid titles and authors")
    void testInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> new Book(null, "Author"));
        assertThrows(IllegalArgumentException.class, () -> new Book("   ", "Author"));
        assertThrows(IllegalArgumentException.class, () -> new Book("Title", null));
        assertThrows(IllegalArgumentException.class, () -> new Book("Title", "   "));
        assertThrows(IllegalArgumentException.class, () -> new Book("Title", "Author", 0));
        assertThrows(IllegalArgumentException.class, () -> new Book("Title", "Author", -5));
    }

    @Test
    @DisplayName("Should satisfy equals and hashCode for identical title and author")
    void testEquality() {
        Book bookA = new Book("Dune", "Frank Herbert");
        Book bookB = new Book("dune", "FRANK HERBERT");

        assertEquals(bookA, bookB);
        assertEquals(bookA.hashCode(), bookB.hashCode());
    }
}
