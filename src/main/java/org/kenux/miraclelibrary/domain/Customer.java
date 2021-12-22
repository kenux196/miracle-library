package org.kenux.miraclelibrary.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Customer {

    private Long id;
    private String name;
    private String email;
    private String password;

    @Builder
    public Customer(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
