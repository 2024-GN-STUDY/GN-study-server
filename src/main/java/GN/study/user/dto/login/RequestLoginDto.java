package GN.study.user.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 로그인 요청 DTO, JWT에 사용
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestLoginDto {

    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    public String email;

    @Size(max = 20)
    @NotBlank(message = "패스워드를 입력해주세요. ")
    public String password;
}
