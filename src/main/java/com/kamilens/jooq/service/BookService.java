package com.kamilens.jooq.service;

import com.kamilens.jooq.domain.Book;

import java.util.Set;

public interface BookService {

    Book create(Book book);

    Set<Book> search();

    Book getById(Long id);

}
