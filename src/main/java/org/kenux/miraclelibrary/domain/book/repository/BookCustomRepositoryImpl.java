package org.kenux.miraclelibrary.domain.book.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookInfo;
import org.kenux.miraclelibrary.web.book.dto.request.BookSearchFilter;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static org.kenux.miraclelibrary.domain.book.domain.QBookInfo.bookInfo;

@Repository
@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<BookInfo> findBookByFilter(BookSearchFilter bookSearchFilter) {
        return jpaQueryFactory.selectFrom(bookInfo)
                .where(eqCategory(bookSearchFilter.getCategory()),
                        titleOrAuthorContains(bookSearchFilter.getKeyword()))
                .fetch();
    }

    @Override
    public List<BookInfo> findNewBookWithinOneMonth(LocalDate time) {
        LocalDate findDate = time.minusMonths(1);
        return jpaQueryFactory.selectFrom(bookInfo)
                .where(bookInfo.publishDate.after(findDate))
                .fetch();
    }

    private BooleanExpression titleOrAuthorContains(String keyword) {
        if (keyword == null) {
            return null;
        }
        return bookInfo.title.contains(keyword)
                .or(bookInfo.author.contains(keyword));
    }

    private BooleanExpression eqCategory(BookCategory category) {
        if (category == null) {
            return null;
        }

        return bookInfo.category.eq(category);
    }
}