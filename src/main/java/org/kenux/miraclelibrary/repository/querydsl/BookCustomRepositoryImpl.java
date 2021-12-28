package org.kenux.miraclelibrary.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kenux.miraclelibrary.domain.QBook.book;

@Repository
@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Book> findAllByKeyword(String keyword) {
        return jpaQueryFactory.selectFrom(book)
                .where(book.title.contains(keyword).or(book.author.contains(keyword)))
                .fetch();
    }
}