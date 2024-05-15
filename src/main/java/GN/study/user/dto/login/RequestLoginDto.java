package GN.study.user.dto.login;

import lombok.Builder;
import lombok.Getter;

// 로그인 요청 DTO, JWT에 사용
@Getter
@Builder
public class RequestLoginDto {

    public String email;
    public String password;
}
