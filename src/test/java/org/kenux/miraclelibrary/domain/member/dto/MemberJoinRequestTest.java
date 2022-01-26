package org.kenux.miraclelibrary.domain.member.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.domain.MemberRole;
import org.kenux.miraclelibrary.domain.member.domain.MemberStatus;

import static org.assertj.core.api.Assertions.assertThat;

class MemberJoinRequestTest {

    private MemberJoinRequest memberJoinRequest;

    @BeforeEach
    void setup() {
        memberJoinRequest = createMemberJoinRequest();
    }

    @Test
    @DisplayName("회원 가입 요청 검증")
    void create() throws Exception {
        assertThat(memberJoinRequest.getEmail()).isNotNull();
        assertThat(memberJoinRequest.getName()).isNotNull();
        assertThat(memberJoinRequest.getPassword()).isNotNull();
        assertThat(memberJoinRequest.getPhone()).isNotNull();
    }

    @Test
    @DisplayName("멤버 조인 요청을 멤버 엔티티로 변환")
    void toEntity() throws Exception {
        // given
        // when
        Member member = memberJoinRequest.toEntity();

        // then
        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo(memberJoinRequest.getName());
        assertThat(member.getPassword()).isEqualTo(memberJoinRequest.getPassword());
        assertThat(member.getEmail()).isEqualTo(memberJoinRequest.getEmail());
        assertThat(member.getId()).isNull();
    }

    @Test
    @DisplayName("멤버 엔티티로 변화 시, 멤버의 롤이 지정되어야 한다.")
    void toEntity_MemberRole() throws Exception {
        // given
        // when
        Member member = memberJoinRequest.toEntity();

        // then
        assertThat(member.getMemberRole()).isEqualTo(MemberRole.CUSTOMER);
    }

    @Test
    @DisplayName("멤버 엔티티로 변화 시, 멤버 상태 노말 설정")
    void toEntity_hasStatus() throws Exception {
        // given
        // when
        Member member = memberJoinRequest.toEntity();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.NORMAL);
    }

    private MemberJoinRequest createMemberJoinRequest() {
        return MemberJoinRequest.builder()
                .name("user")
                .email("user@test.com")
                .phone("010-1234-1234")
                .password("password")
                .build();
    }

}