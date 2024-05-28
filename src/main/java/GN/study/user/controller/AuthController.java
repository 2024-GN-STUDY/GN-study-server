package GN.study.user.controller;

import GN.study.config.JwtUtil;
import GN.study.user.dto.login.RequestLoginDto;
import GN.study.user.dto.login.ResponseLoginDto;
import GN.study.user.entity.User;
import GN.study.user.exception.UserNotFoundException;
import GN.study.user.repository.RedisRepository;
import GN.study.user.repository.UserRepository;
import GN.study.user.service.AuthService;
import GN.study.user.token.RefreshToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RedisRepository redisRepository;

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDto> login(@Valid @RequestBody RequestLoginDto requestLoginDto, HttpServletResponse response) {

        ResponseLoginDto responseLoginDto = authService.login(requestLoginDto);

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



    /**
     * refreshToken 재발급
     *
     * @param headerRefreshToken
     * @return RefreshToken
     */
    @PostMapping("/refreshTokenReissuance")
    public ResponseEntity<ResponseLoginDto> refreshTokenReissuance(@RequestHeader("Authorization") String headerRefreshToken, HttpServletResponse httpServletResponse) {

        String newAccessToken = "";
        String newRefreshToken = "";

        //1.redis 에 리프레시 토큰이 있는지 없는지 체크
        Optional<RefreshToken> isValidRefreshToken = authService.refreshTokenReissuance(headerRefreshToken);
        if (isValidRefreshToken != null && !isValidRefreshToken.isEmpty()) {
            String refreshToken = headerRefreshToken.substring(7);

            Long userId = jwtUtil.extractUserId(refreshToken);
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));

            newAccessToken = jwtUtil.generateAccessToken(user.getEmail(), userId);
            newRefreshToken = jwtUtil.generateRefreshToken(user.getEmail(), userId);

            // 2. 엑세스 토큰 재발급
            if (userId == user.getId()) {
                // 쿠키에 넣어서 return, 기존꺼는 redis 삭제 처리
                redisRepository.save(new RefreshToken(newRefreshToken, user.getName()));
                redisRepository.deleteById(refreshToken);
            }

            ResponseLoginDto responseLoginDto = new ResponseLoginDto(newAccessToken, newRefreshToken);

            ResponseCookie accessCookie = ResponseCookie.from("Access-Token", responseLoginDto.getAccessToken())
                    .maxAge(60 * 60)
                    .httpOnly(false)
                    .path("/")
                    .build();

            ResponseCookie refreshCookie = ResponseCookie.from("Refresh-Token", responseLoginDto.getRefreshToken())
                    .maxAge(3 * 24 * 60 * 60)
                    .httpOnly(true)
                    .path("/")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                    .body(responseLoginDto);
        }
        return ResponseEntity.badRequest().build();
    }
}
