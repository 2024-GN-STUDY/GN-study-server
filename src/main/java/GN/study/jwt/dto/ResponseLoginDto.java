package GN.study.jwt.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 로그인 응답 DTO
@Getter
@RequiredArgsConstructor
public class ResponseLoginDto {

    public final String accessToken;
    public final String refreshToken;
}
