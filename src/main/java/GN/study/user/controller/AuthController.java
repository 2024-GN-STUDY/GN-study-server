package GN.study.user.controller;

import GN.study.user.dto.login.RequestLoginDto;
import GN.study.user.dto.login.ResponseLoginDto;
import GN.study.user.exception.UserNotFoundException;
import GN.study.user.service.AuthService;
import GN.study.user.token.RefreshToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

            ResponseLoginDto responseLoginDto = null;
        try{
            responseLoginDto = authService.login(requestLoginDto);
        } catch (Exception e){
            throw new UserNotFoundException("USER NOT FOUND");
        }

        // Access Token을 일반 쿠키로 설정
        Cookie accessTokenCookie = new Cookie("Access-Token", responseLoginDto.getAccessToken());
        accessTokenCookie.setMaxAge(60 * 60); // 1시간
        accessTokenCookie.setHttpOnly(false); // 클라이언트 스크립트에서 접근 가능
        accessTokenCookie.setPath("/"); // 경로 설정
        response.addCookie(accessTokenCookie);

        // Refresh Token을 HttpOnly 쿠키로 설정
        Cookie refreshTokenCookie = new Cookie("Refresh-Token", responseLoginDto.getRefreshToken());
        refreshTokenCookie.setMaxAge(3 * 24 * 60 * 60); // 1주일
        refreshTokenCookie.setHttpOnly(true); // 클라이언트 스크립트에서 접근 불가능
        refreshTokenCookie.setPath("/"); // 경로 설정
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@RequestHeader("Authorization") String headerAccessToken, @RequestBody RefreshToken refreshToken){

        String accessToken = "";

        if(headerAccessToken != null && headerAccessToken.startsWith("Bearer ")){
            accessToken = headerAccessToken.substring(7);
            authService.logout(accessToken, refreshToken.getRefreshToken());
        } else{
            ResponseEntity.status(HttpStatus.UNAUTHORIZED);
        }

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
