package org.kenux.miraclelibrary.domain.member.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberPasswordTest {

    @Test
    void create() {
        final MemberPassword password = new MemberPassword("");
        assertThat(password).isNotNull();
    }

    @Test
    void change() {
        final MemberPassword password = new MemberPassword("");
        password.change("password");
        assertThat(password.getPassword()).isEqualTo("password");
    }
}