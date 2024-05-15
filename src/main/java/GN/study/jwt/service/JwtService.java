package GN.study.jwt.service;

import GN.study.jwt.util.JwtUtil;
import GN.study.user.dto.login.RequestLoginDto;
import GN.study.user.dto.login.ResponseLoginDto;
import GN.study.redis.repository.RedisRepository;
import GN.study.redis.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtService {

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final RedisRepository redisRepository;

    @Transactional
    public ResponseLoginDto createToken(RequestLoginDto requestLoginDto){

        // 입력받은 로그인 정보로 UsernamePasswordAuthenticationToken 생성
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestLoginDto.getEmail(), requestLoginDto.getPassword()));

        // authentication 객체에서 UserDetails 추출(사용자의 상세정보 포함)
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // JWT 토큰 생성
        final String accessToken = jwtUtil.generateAccessToken(userDetails.getUsername());
        final String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

        redisRepository.save(new RefreshToken(refreshToken, userDetails.getUsername()));

        return ResponseLoginDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}