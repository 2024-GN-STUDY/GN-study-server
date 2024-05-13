package GN.study.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@Schema(description = "회원가입 요청 Dto 객체")
public class RequestUserSignUpDto {

    @NotBlank
    @Email
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+[.][a-zA-Z]{2,3}$")
    @Schema(title = "사용자 이메일")
    private String email;

    @Size(max = 20)
    @NotBlank
    @Schema(title = "사용자 패스워드")
    private String password;

    @Size(min = 2, max = 50)
    @NotBlank
    @Schema(title = "사용자 이름")
    private String name;

    @Schema(title = "사용자 생년월일")
    private Date birth_dt;

    @Size(max = 13)
    @NotBlank
    @Schema(title = "사용자 휴대폰번호")
    private String phone_num;

    @Schema(title = "사용자 나이")
    private Integer age;

    @Schema(title = "사용자 기본주소")
    private String baseAddr;

    @Schema(title = "사용자 상세주소")
    private String detailAddr;

}
