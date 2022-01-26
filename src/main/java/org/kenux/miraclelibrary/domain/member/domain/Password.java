package org.kenux.miraclelibrary.domain.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "password")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password", nullable = false)
    private String password;

    public Password(String password) {
        this.password = password;
    }

    public void change(String password) {
        this.password = password;
    }
}
