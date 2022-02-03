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

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private String address;

    private LocalDateTime lastAccessTime;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id")
    private MemberPassword memberPassword;

    @Builder
    public Member(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void changePassword(String password) {
        if (this.memberPassword == null) {
            this.memberPassword = new MemberPassword();
        }
        this.memberPassword.change(password);
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

    public void changeStatus(MemberStatus status) {
        this.status = status;
    }

    public void changeRole(MemberRole role) {
        this.role = role;
    }

    public String getMemberPassword() {
        return this.memberPassword.getPassword();
    }

    public static Member createCustomer(String name, String email, String phone, String password) {
        final Member member = new Member(name, email, phone);
        member.changePassword(password);
        member.changeStatus(MemberStatus.NORMAL);
        member.changeRole(MemberRole.CUSTOMER);
        return member;
    }

    public static Member createLibrarian(String name, String email, String phone, String password) {
        final Member member = new Member(name, email, phone);
        member.changePassword(password);
        member.changeStatus(MemberStatus.NORMAL);
        member.changeRole(MemberRole.LIBRARIAN);
        return member;
    }
}
