package GN.study.user.service;

import GN.study.user.dto.login.RequestLoginDto;
import GN.study.user.dto.login.ResponseLoginDto;
import GN.study.user.repository.RedisRepository;
import GN.study.user.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final JwtTokenService jwtTokenService;
    private final RedisRepository redisRepository;

    @Transactional
    public ResponseLoginDto login(RequestLoginDto requestLoginDto){
        return jwtTokenService.createToken(requestLoginDto);
    }

    @Transactional
    public void logout(String accessToken, String refreshToken) {
        jwtTokenService.addBlackList(accessToken);
        jwtTokenService.deleteRefreshToken(refreshToken);
    }

    /**
     *
     * @param refreshToken 재발급
     * @return RefreshToken
     */
    @Transactional
    public Optional<RefreshToken> refreshTokenReissuance(String refreshToken) {
        return redisRepository.findByRefreshToken(refreshToken);
    }

    /**
     * 쿠키 만료
     */
//    public ResponseLogoutDto ExpiredCookie(RequestLogoutDto requestLogoutDto) {
//
//        Cookie acceessCookie = new Cookie(requestLogoutDto.getAccessTokenCookie(), null);
//        acceessCookie.setMaxAge(0);
//
//        Cookie refreshCookie = new Cookie(requestLogoutDto.getRefreshTokenCookie(), null);
//        refreshCookie.setMaxAge(0);
//        return new ResponseLogoutDto(acceessCookie, refreshCookie);
//    }



}
