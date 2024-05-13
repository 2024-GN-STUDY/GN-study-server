package GN.study.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "Users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "User Entity")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)     // nullable : false -> not null
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;

    private String phone_num;

    private Integer age;

    private Date birth_dt;

    @Builder
    public User(Long id, String name, String password, String email, Role role, Address address, String phone_num, Integer age, Date birth_dt) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.address = address;
        this.phone_num = phone_num;
        this.age = age;
        this.birth_dt = birth_dt;
    }
}