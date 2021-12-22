package org.kenux.miraclelibrary.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    @DisplayName("고객은 id, name, password, email 을 가진다.")
    void test_CustomerBasic() {
        final Customer customer = new Customer("name", "email", "password");

        assertThat(customer.getName()).isEqualTo("name");
        assertThat(customer.getEmail()).isEqualTo("email");
        assertThat(customer.getPassword()).isEqualTo("password");
    }

    @Test
    @DisplayName("고객은 패스워드 변경을 할 수 있어야 한다.")
    void test_changePassword() {
        final Customer customer = new Customer("name", "email", "password");

        customer.changePassword("password1");

        assertThat(customer.getPassword()).isEqualTo("password1");
    }

    @Test
    @DisplayName("고객은 이메일을 변경할 수 있어야 한다.")
    void test_changeEmail() {
        final Customer customer = new Customer("name", "email", "password");

        customer.changeEmail("changedEmail");

        assertThat(customer.getEmail()).isEqualTo("changedEmail");
    }

    @Test
    @DisplayName("고객은 이름을 변경할 수 있다.")
    void test_changeName() {
        final Customer customer = new Customer("name", "name@email.com", "password");

        customer.changeName("changedName");

        assertThat(customer.getName()).isEqualTo("changedName");
    }
}