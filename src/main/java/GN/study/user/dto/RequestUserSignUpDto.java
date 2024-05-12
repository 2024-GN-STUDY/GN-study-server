package GN.study.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
public class RequestUserSignUpDto {

    @NotBlank
    @Email
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+[.][a-zA-Z]{2,3}$")
    private String email;

    @Size(max = 20)
    @NotBlank
    private String password;

    @Size(min = 2, max = 50)
    @NotBlank
    private String name;

    private Date birth_dt;

    @Size(max = 13)
    @NotBlank
    private String phone_num;

    private Integer age;

    private String baseAddr;

    private String detailAddr;

}
