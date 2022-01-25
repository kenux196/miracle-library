package org.kenux.miraclelibrary.domain.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.global.entity.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_password_id")
    private MemberPassword password;

    @Column(name = "member_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    private String address;

    private LocalDateTime lastAccessTime;

    @Builder
    public Member(String name, String email, String phone, String password, MemberRole memberRole) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = new MemberPassword(password);
        this.memberRole = memberRole;
    }

    public void changePassword(String password) {
        this.password.change(password);
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePhone(String phone) {
        this.phone = phone;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void updateLastAccessTime(LocalDateTime accessTime) {
        this.lastAccessTime = accessTime;
    }

    public String getPassword() {
        return this.password.getPassword();
    }
}
