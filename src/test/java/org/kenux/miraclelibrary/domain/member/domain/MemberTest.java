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
    void test_CustomerBasic() {
        assertThat(member.getName()).isEqualTo("name");
        assertThat(member.getEmail()).isEqualTo("email");
        assertThat(member.getPassword()).isEqualTo("password");
    }

    @Test
    @DisplayName("멤버는 패스워드 변경을 할 수 있어야 한다.")
    void test_changePassword() {
        member.changePassword("password1");
        assertThat(member.getPassword()).isEqualTo("password1");
    }

    @Test
    @DisplayName("멤버는 이메일을 변경할 수 있어야 한다.")
    void test_changeEmail() {
        member.changeEmail("changedEmail");
        assertThat(member.getEmail()).isEqualTo("changedEmail");
    }

    @Test
    @DisplayName("멤버는 이름을 변경할 수 있다.")
    void test_changeName() {
        member.changeName("changedName");
        assertThat(member.getName()).isEqualTo("changedName");
    }

    @Test
    @DisplayName("멤버 연락처 변경")
    void changePhoneNumber() throws Exception {
        member.changePhone("010-1111-2345");
        assertThat(member.getPhone()).isEqualTo("010-1111-2345");
    }

    @Test
    @DisplayName("회원 주소 변경")
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


    private Member createMember() {
        return Member.builder()
                .name("name")
                .email("email")
                .phone("010-1234-0987")
                .password("password")
                .memberRole(MemberRole.CUSTOMER)
                .build();
    }
}