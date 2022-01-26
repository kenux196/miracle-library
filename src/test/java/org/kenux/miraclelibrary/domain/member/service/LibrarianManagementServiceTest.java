package org.kenux.miraclelibrary.domain.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.domain.MemberRole;
import org.kenux.miraclelibrary.domain.member.dto.LibrarianAddRequest;
import org.kenux.miraclelibrary.domain.member.repository.MemberRepository;
import org.kenux.miraclelibrary.global.exception.CustomException;
import org.kenux.miraclelibrary.global.exception.ErrorCode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LibrarianManagementServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    LibrarianManagementService librarianManagementService;

    @Test
    @DisplayName("관리자는 매니저 추가 시, 이메일 중복 체크되어야 한다.")
    void emailDuplicateTest_whenAddLibrarian() throws Exception {
        // given
        LibrarianAddRequest librarianAddRequest = LibrarianAddRequest.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("password")
                .build();

        given(memberRepository.existsByEmail(any())).willReturn(true);

        // when then
        assertThatThrownBy(() -> librarianManagementService.addLibrarian(librarianAddRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.EMAIL_DUPLICATION.getMessage());
    }

    @Test
    @DisplayName("관리자는 사서를 추가할 수 있다.")
    void test_addLibrarian() throws Exception {
        // given
        Member member = Member.builder()
                .name("librarian1")
                .email("librarian1@test.com")
                .memberRole(MemberRole.LIBRARIAN)
                .build();
        ReflectionTestUtils.setField(member, "id", 1L);
        given(memberRepository.save(any())).willReturn(member);

        // when
        LibrarianAddRequest librarianAddRequest = LibrarianAddRequest.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("password")
                .build();
        Member saved = librarianManagementService.addLibrarian(librarianAddRequest);

        // then
        assertThat(saved.getId()).isEqualTo(1);
        assertThat(saved.getName()).isEqualTo("librarian1");
        assertThat(saved.getEmail()).isEqualTo("librarian1@test.com");
        assertThat(saved.getMemberRole()).isEqualTo(MemberRole.LIBRARIAN);
    }
}