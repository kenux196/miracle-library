package org.kenux.miraclelibrary.domain.book.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.web.book.dto.request.BookSearchFilter;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static org.kenux.miraclelibrary.domain.book.domain.QBook.book;

@Repository
@RequiredArgsConstructor
public class BookInfoCustomRepositoryImpl implements BookInfoCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Book> findBookByFilter(BookSearchFilter bookSearchFilter) {
        return jpaQueryFactory.selectFrom(book)
                .where(eqCategory(bookSearchFilter.getCategory()),
                        titleOrAuthorContains(bookSearchFilter.getKeyword()))
                .fetch();
    }

    @Override
    public List<Book> findNewBookWithinOneMonth() {
        LocalDate findDate = LocalDate.now().minusMonths(1);
        return jpaQueryFactory.selectFrom(book)
                .where(book.publishDate.after(findDate))
                .fetch();
    }

    private BooleanExpression titleOrAuthorContains(String keyword) {
        if (keyword == null) {
            return null;
        }
        return book.title.contains(keyword)
                .or(book.author.contains(keyword));
    }

    private BooleanExpression eqCategory(BookCategory category) {
        if (category == null) {
            return null;
        }
        return book.category.eq(category);
    }
}