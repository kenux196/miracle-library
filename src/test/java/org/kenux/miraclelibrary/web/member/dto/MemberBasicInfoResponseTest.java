package org.kenux.miraclelibrary.web.member.dto;

import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.web.member.dto.response.MemberBasicInfoResponse;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class MemberBasicInfoResponseTest {

    @Test
    void from_엔티티로부터_생성() throws Exception {
        // given
        Member member = Member.createCustomer(
                "member1", "email", "010-1234-1234", "password");
        ReflectionTestUtils.setField(member, "id", 1L);

        // when
        MemberBasicInfoResponse response = MemberBasicInfoResponse.from(member);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(member.getId());
        assertThat(response.getName()).isEqualTo(member.getName());
        assertThat(response.getEmail()).isEqualTo(member.getEmail());
        assertThat(response.getRole()).isEqualTo(member.getRole());
        assertThat(response.getStatus()).isEqualTo(member.getStatus());
    }

}