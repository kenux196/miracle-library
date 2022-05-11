package org.kenux.miraclelibrary.domain.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.web.member.dto.MemberBasicInfoResponse;
import org.kenux.miraclelibrary.domain.member.repository.MemberRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberFindServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberFindService memberFindService;

    private Member member;

    @BeforeEach
    void setup() {
        member = getMember();
    }

    @Test
    @DisplayName("getMembers: 전체회원 조회")
    void getMembers_전체회원조회() {
        // given
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String name = "customer" + 1;
            String email = name + "@email.com";
            final Member member = Member.createCustomer(name, email, "010-1234-5678", "password");
            members.add(member);
        }
        given(memberRepository.findAll()).willReturn(members);

        // when
        List<MemberBasicInfoResponse> memberList = memberFindService.getMembers();

        // then
        assertThat(memberList).hasSize(100);
    }

    @Test
    @DisplayName("getMembersByName: 회원 이름으로 조회")
    void getMembersByName_회원이름으로조회() {
        // given
        given(memberRepository.findByName(any())).willReturn(Optional.of(member));

        // when
        Optional<Member> result = memberFindService.getMembersByName("customer1");

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getName()).isEqualTo("customer1");
    }

    @Test
    @DisplayName("getMember: 회원 아이디를 통한 회원 조회")
    void getMember_회원아이디로조회() {
        // given
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        // when
        Optional<Member> result = memberFindService.getMember(1L);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    private Member getMember() {
        Member member = Member.createCustomer(
                "customer1", "customer1@test.com", "010-1234-5678", "password");
        ReflectionTestUtils.setField(member, "id", 1L);
        return member;
    }


}