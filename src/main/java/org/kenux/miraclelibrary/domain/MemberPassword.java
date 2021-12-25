package org.kenux.miraclelibrary.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "password")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password", nullable = false)
    private String password;

    public MemberPassword(String password) {
        this.password = password;
    }

    public void change(String password) {
        this.password = password;
    }
}
