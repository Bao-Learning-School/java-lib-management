package com.oakwood.library.factory;

import com.oakwood.library.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BookFactory Creational Pattern Tests")
class BookFactoryTest {

    @Test
    @DisplayName("Should create standard book with 14 due days")
    void testStandardBookCreation() {
        Book book = BookFactory.createStandardBook("The Hobbit", "J.R.R. Tolkien");

        assertEquals("The Hobbit", book.getTitle());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
        assertEquals(BookFactory.STANDARD_LOAN_DAYS, book.getDueDays());
        assertTrue(book.isAvailable());
    }

    @Test
    @DisplayName("Should create bestseller with 7 due days")
    void testBestsellerCreation() {
        Book book = BookFactory.createBestseller("1984", "George Orwell");

        assertEquals(BookFactory.BESTSELLER_LOAN_DAYS, book.getDueDays());
    }

    @Test
    @DisplayName("Should create reference book with 3 due days")
    void testReferenceBookCreation() {
        Book book = BookFactory.createReferenceBook("Encyclopedia", "Britannica");

        assertEquals(BookFactory.REFERENCE_LOAN_DAYS, book.getDueDays());
    }

    @Test
    @DisplayName("Should create children's book with 21 due days")
    void testChildrenBookCreation() {
        Book book = BookFactory.createChildrenBook("The Cat in the Hat", "Dr. Seuss");

        assertEquals(BookFactory.CHILDREN_LOAN_DAYS, book.getDueDays());
    }
}
