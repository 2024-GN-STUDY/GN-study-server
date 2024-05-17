package GN.study.user.controller;

import GN.study.user.dto.login.RequestLoginDto;
import GN.study.user.dto.login.ResponseLoginDto;
import GN.study.user.service.AuthService;
import GN.study.user.token.RefreshToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDto> login(@Valid @RequestBody RequestLoginDto requestLoginDto, HttpServletResponse response) {

        ResponseLoginDto responseLoginDto = authService.login(requestLoginDto);

        // HttpHeaders headers = new HttpHeaders();
        // headers.set("Access-Token", responseLoginDto.getAccessToken());
        // headers.set("Refresh-Token", responseLoginDto.getRefreshToken());

        // // 1번 방식
        Cookie accessToken = new Cookie("Access_Token", responseLoginDto.getAccessToken());
        accessToken.setMaxAge(60 * 60); // 1시간
        accessToken.setHttpOnly(true);
        accessToken.setPath("/");
        response.addCookie(accessToken);

        // // 2번 방식
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.SET_COOKIE,
                "Refresh_Token=" + responseLoginDto.getRefreshToken()
                        + "; Max-Age=3600; Path=/; HttpOnly");

        return ResponseEntity.ok().headers(headers).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@RequestHeader("Authorization") String headerAccessToken,
            @RequestBody RefreshToken refreshToken) {

        String accessToken = "";

        if (headerAccessToken != null && headerAccessToken.startsWith("Bearer ")) {
            accessToken = headerAccessToken.substring(7);
            authService.logout(accessToken, refreshToken.getRefreshToken());
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED);
        }

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
