package GN.study.user.dto;

import GN.study.user.entity.Role;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
public class ResponseUserSignUpDto {

    private Long id;

    private String email;

    private String name;

    private String phone_num;

    private Integer age;

    private String baseAddr;

    private String detailAddr;

    private Date birth_dt;

    private Role role;

}
