package GN.study.user.dto.login;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

// 로그인 응답 DTO
@Getter
@Setter
@Builder
public class ResponseLoginDto {

    public String accessToken;
    public String refreshToken;
}
