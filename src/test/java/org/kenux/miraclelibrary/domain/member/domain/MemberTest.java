package org.kenux.miraclelibrary.domain.member.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    private Member member;

    @BeforeEach
    void setUp() {
        member = createMember();
    }

    @Test
    @DisplayName("멤버 생성: 일반 회원")
    void createCustomer() {
        Member member = Member.createCustomer(
                "customer", "customer@test.com", "010-1111-2222", "password");
        assertThat(member.getRole()).isEqualTo(MemberRole.CUSTOMER);
    }

    @Test
    @DisplayName("멤버 생성: 사서")
    void createLibrarian() {
        Member member = Member.createLibrarian(
                "librarian", "librarian@test.com", "010-1111-2222", "password");
        assertThat(member.getRole()).isEqualTo(MemberRole.LIBRARIAN);
    }

    @Test
    void changePassword() {
        member.changePassword("password1");
        assertThat(member.getMemberPassword()).isEqualTo("password1");
    }

    @Test
    void changePassword_failed() {
        // TODO : 패스워드 변경 규칙 적용 - skyun 2022-01-27
        // 현재 패스워드와 동일한지 확인
        // 패스워드 규칙에 따르는지 확인.
        // 패스워드 규칙 확인하는 기능 추가
    }

    @Test
    void changeName() {
        member.changeName("changedName");
        assertThat(member.getName()).isEqualTo("changedName");
    }

    @Test
    void changePhoneNumber() throws Exception {
        member.changePhone("010-1111-2345");
        assertThat(member.getPhone()).isEqualTo("010-1111-2345");
    }

    @Test
    void changeAddress() throws Exception {
        member.changeAddress("대구시 달성군");
        assertThat(member.getAddress()).isEqualTo("대구시 달성군");
    }

    @Test
    @DisplayName("회원 최종 접속일")
    void updateLastAccess() throws Exception {
        // given
        LocalDateTime accessTime = LocalDateTime.of(
                2022, 1, 22, 12, 12, 12);
        // when
        member.updateLastAccessTime(accessTime);
        // then
        assertThat(member.getLastAccessTime()).isEqualTo(accessTime);
    }

    @Test
    @DisplayName("회원 상태 변경: 휴면")
    void changeStatus_휴면상태() throws Exception {
        member.changeStatus(MemberStatus.DORMANCY);
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DORMANCY);
    }

    @Test
    @DisplayName("회원 상태 변경: 정상")
    void changeStatus_정상() throws Exception {
        member.changeStatus(MemberStatus.NORMAL);
        assertThat(member.getStatus()).isEqualTo(MemberStatus.NORMAL);
    }

    private Member createMember() {
        return Member.createCustomer(
                "member1", "member1@test.com", "010-1234-5678", "password");
    }
}