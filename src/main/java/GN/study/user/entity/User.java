package GN.study.user.entity;

import GN.study.user.dto.RequestUserDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "Users")
@Getter
@Builder
public class User {

    @Id
    @Column(name = "user_id")
    private Long id;

    private String name;

}