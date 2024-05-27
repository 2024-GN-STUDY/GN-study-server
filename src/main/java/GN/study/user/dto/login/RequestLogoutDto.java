package GN.study.user.dto.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestLogoutDto {

    private final String refreshToken;

    private final String accessTokenCookie;

    private final String refreshTokenCookie;

}
