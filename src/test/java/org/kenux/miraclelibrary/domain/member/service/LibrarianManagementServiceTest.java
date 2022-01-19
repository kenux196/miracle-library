package org.kenux.miraclelibrary.domain.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.domain.MemberRole;
import org.kenux.miraclelibrary.domain.member.dto.LibrarianAddRequest;
import org.kenux.miraclelibrary.domain.member.repository.MemberRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibrarianManagementServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    LibrarianManagementService librarianManagementService;

    @Test
    @DisplayName("관리자는 사서를 추가할 수 있다.")
    void test_addLibrarian() throws Exception {
        // given
        Member member = Member.builder()
                .name("librarian1")
                .email("librarian1@test.com")
                .password("password")
                .memberRole(MemberRole.LIBRARIAN)
                .build();;
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.save(any())).thenReturn(member);

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