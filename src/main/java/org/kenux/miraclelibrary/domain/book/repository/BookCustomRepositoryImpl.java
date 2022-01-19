package org.kenux.miraclelibrary.domain.book.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static org.kenux.miraclelibrary.domain.QBook.book;

@Repository
@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Book> findAllByKeyword(String keyword) {
        return jpaQueryFactory.selectFrom(book)
                .where(titleOrAuthorContains(keyword))
                .fetch();
    }

    private BooleanExpression titleOrAuthorContains(String keyword) {
        if (keyword == null) {
            return null;
        }
        return book.title.contains(keyword)
                .or(book.author.contains(keyword));
    }

    @Override
    public List<Book> findNewBookWithinOneMonth(LocalDate time) {
        LocalDate findDate = time.minusMonths(1);
        return jpaQueryFactory.selectFrom(book)
                .where(book.publicationDate.after(findDate))
                .fetch();
    }
}