package com.kamilens.jooq.service.impl;

import com.kamilens.jooq.domain.Book;
import com.kamilens.jooq.service.BookService;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.kamilens.jooq.jooq.Tables.BOOKS;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final DSLContext dslContext;

    @Override
    public Book create(Book book) {
        var result = this.dslContext
                .insertInto(BOOKS, BOOKS.TITLE, BOOKS.ABOUT)
                .values(book.getTitle(), book.getAbout())
                .returningResult(BOOKS.ID, BOOKS.TITLE, BOOKS.ABOUT)
                .fetchOne();

        return Book
                .builder()
                .id(result.get(BOOKS.ID))
                .title(result.get(BOOKS.TITLE))
                .about(result.get(BOOKS.ABOUT))
                .build();
    }

    @Override
    public Set<Book> search() {
        return new HashSet<>(this.dslContext.selectFrom(BOOKS).fetchInto(Book.class));
    }

    @Override
    public Book getById(Long id) {
        return Optional
                .ofNullable(
                        this.dslContext
                                .selectFrom(BOOKS)
                                .where(BOOKS.ID.eq(id))
                                .fetchOneInto(Book.class)
                )
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

}
