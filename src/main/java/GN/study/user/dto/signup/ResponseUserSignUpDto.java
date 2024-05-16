package GN.study.user.dto.signup;

import GN.study.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@Schema(description = "회원가입 응답 Dto 객체")
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserSignUpDto {

    @Schema(title = "사용자 아이디")
    private Long id;

    @Schema(title = "사용자 이메일")
    private String email;

    @Schema(title = "사용자 이름")
    private String name;

    @Schema(title = "사용자 휴대폰번호")
    private String phone_num;

    @Schema(title = "사용자 나이")
    private Integer age;

    @Schema(title = "사용자 기본주소")
    private String baseAddr;

    @Schema(title = "사용자 상세주소")
    private String detailAddr;

    @Schema(title = "사용자 생년월일")
    private Date birth_dt;

    @Schema(title = "사용자 권한")
    private Role role;

}
