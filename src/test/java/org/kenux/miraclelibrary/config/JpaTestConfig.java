package org.kenux.miraclelibrary.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TestConfiguration
public class JpaTestConfig {

    @PersistenceContext
    private EntityManager em;

    /**
     * Querydsl 을 DataJpaTest 에서 사용할 수 있도록 하기 위함.
     *
     * @return JPAQueryFactory
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}