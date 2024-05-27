package GN.study.user.service;

import GN.study.config.JwtUtil;
import GN.study.user.dto.login.RequestLoginDto;
import GN.study.user.dto.login.ResponseLoginDto;
import GN.study.user.repository.RedisRepository;
import GN.study.user.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtTokenService {

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    // RefreshToken 관리
    private final RedisRepository redisRepository;

    // AccessToken 관리
    private final RedisTemplate<String, Object> redisTemplate;

    private final UserService userService;

    @Transactional
    public ResponseLoginDto createToken(RequestLoginDto requestLoginDto){

        try {
            // 입력받은 로그인 정보로 UsernamePasswordAuthenticationToken 생성
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestLoginDto.getEmail(), requestLoginDto.getPassword()));

            // authentication 객체에서 UserDetails 추출(사용자의 상세정보 포함)
            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            final Long userId = userService.findUserIdByEmail(requestLoginDto.email);

            // JWT 토큰 생성
            final String accessToken = jwtUtil.generateAccessToken(userDetails.getUsername(), userId);
            final String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername(), userId);

            redisRepository.save(new RefreshToken(refreshToken, userDetails.getUsername()));

            return ResponseLoginDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (Exception e){
            throw new BadCredentialsException("invalid credentials");
        }
    }

    /**
     * 로그아웃 시 블랙리스트 토큰 추가
     * @Param AccessToken
     * */
    public void addBlackList(String token){
        redisTemplate.opsForValue().set(token, "blacklisted", 3600, TimeUnit.SECONDS);
    }

    /**
     * Redis 에서 토큰 삭제
     * @Param RefreshToken
     * */
    public void deleteRefreshToken(String refreshToken){
        
        // 만료 안되어있으면 삭제
        if(!jwtUtil.isTokenExpired(refreshToken)) redisRepository.deleteById(refreshToken);

    }


    /**
     * 토큰이 블랙리스트에 있는지 확인
     * @Param AccessToken
     * @return 존재하면 True, 없으면 False
     * */
    public boolean isTokenBlacklisted(String token){
        return "blacklisted".equals(redisTemplate.opsForValue().get(token));
    }
}
