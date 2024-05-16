package GN.study.user.dto.login;

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

    public String email;
    public String password;
}
