package org.kenux.miraclelibrary.study;

import org.junit.jupiter.api.*;

public class SampleTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("SampleTest.beforeAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("SampleTest.beforeEach");
    }

    @Test
    void 테스트_라이프사이클1() {
        System.out.println("SampleTest.테스트_라이프사이클 - 1");
    }
    @Test
    void 테스트_라이프사이클2() {
        System.out.println("SampleTest.테스트_라이프사이클 - 2");
    }

    @AfterEach
    void afterEach() {
        System.out.println("SampleTest.afterEach");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("SampleTest.afterAll");
    }

}