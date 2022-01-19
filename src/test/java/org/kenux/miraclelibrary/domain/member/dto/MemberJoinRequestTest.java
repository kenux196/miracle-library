package org.kenux.miraclelibrary.domain.member.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.domain.MemberRole;

import static org.assertj.core.api.Assertions.assertThat;

class MemberJoinRequestTest {

    private MemberJoinRequest memberJoinRequest;

    @BeforeEach
    void setup() {
        memberJoinRequest = createMemberJoinRequest();
    }

    @Test
    @DisplayName("멤버 조인 요청을 멤버 엔티티로 변환")
    void test_toEntity() throws Exception {
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
    void test_setMemberRole_toEntity() throws Exception {
        // given
        // when
        Member member = memberJoinRequest.toEntity();

        // then
        assertThat(member.getMemberRole()).isEqualTo(MemberRole.CUSTOMER);
    }

    private MemberJoinRequest createMemberJoinRequest() {
        return new MemberJoinRequest("member1", "member1@test.com", "password");
    }

}