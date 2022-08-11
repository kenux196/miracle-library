package org.kenux.miraclelibrary.domain.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.repository.MemberRepository;
import org.kenux.miraclelibrary.global.exception.CustomException;
import org.kenux.miraclelibrary.global.exception.ErrorCode;
import org.kenux.miraclelibrary.web.member.dto.LibrarianJoinRequestBuilder;
import org.kenux.miraclelibrary.web.member.dto.request.LibrarianJoinRequest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LibrarianJoinServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    LibrarianJoinService librarianJoinService;

    @Test
    @DisplayName("관리자는 매니저 추가 시, 이메일 중복 체크되어야 한다.")
    void emailDuplicateTest_whenAddLibrarian() throws Exception {
        // given
        LibrarianJoinRequest librarianJoinRequest =
                LibrarianJoinRequestBuilder.build(
                        "user1", "user1@test.com", "010-1234-1234", "password");

        given(memberRepository.existsByEmail(any())).willReturn(true);

        // when then
        assertThatThrownBy(() -> librarianJoinService.add(librarianJoinRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.EMAIL_DUPLICATION.getMessage());
    }

    @Test
    @DisplayName("addLibrarian: 사서 추가 성공시, 회원번호 반환")
    void addLibrarian() throws Exception {
        // given
        LibrarianJoinRequest librarianJoinRequest =
                LibrarianJoinRequestBuilder.build(
                        "user1", "user1@test.com", "010-1234-1234", "password");
        Member member = Member.builder().build();
        ReflectionTestUtils.setField(member, "id", 1L);
        given(memberRepository.save(any())).willReturn(member);

        // when
        Long id = librarianJoinService.add(librarianJoinRequest);

        // then
        assertThat(id).isEqualTo(1L);
    }
}