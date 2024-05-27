package GN.study.user.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

// 로그인 응답 DTO
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResponseLoginDto {

    public String accessToken;
    public String refreshToken;
}
