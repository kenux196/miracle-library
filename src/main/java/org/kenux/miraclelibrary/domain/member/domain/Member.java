package org.kenux.miraclelibrary.domain.member.domain;

import lombok.*;
import org.kenux.miraclelibrary.global.entity.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
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

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    private String address;

    private LocalDateTime lastAccessTime;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id")
    private Password password;

    public void changePassword(String password) {
        if (this.password == null) {
            this.password = new Password();
        }
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

    public void changeStatus(MemberStatus status) {
        this.status = status;
    }

    public String getPassword() {
        return this.password.getPassword();
    }
}
