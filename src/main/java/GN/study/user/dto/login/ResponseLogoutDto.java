package GN.study.user.dto.login;

import jakarta.servlet.http.Cookie;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseLogoutDto {

    private final Cookie accessCookie;

    private final Cookie refreshCookie;
}
