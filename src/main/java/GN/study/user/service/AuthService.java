package GN.study.user.service;

import GN.study.user.dto.login.RequestLoginDto;
import GN.study.user.dto.login.ResponseLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final JwtTokenService jwtTokenService;

    @Transactional
    public ResponseLoginDto login(RequestLoginDto requestLoginDto){
        return jwtTokenService.createToken(requestLoginDto);
    }

    @Transactional
    public void logout(String accessToken, String refreshToken) {
        jwtTokenService.addBlackList(accessToken);
        jwtTokenService.deleteRefreshToken(refreshToken);
    }

}
