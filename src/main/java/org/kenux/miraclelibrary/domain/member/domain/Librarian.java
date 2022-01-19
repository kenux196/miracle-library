package org.kenux.miraclelibrary.domain.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "librarian")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Librarian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Builder
    public Librarian(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
