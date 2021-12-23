package org.kenux.miraclelibrary.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CustomerJoinDto {

    private String name;
    private String email;
    private String password;

    @Builder
    public CustomerJoinDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}