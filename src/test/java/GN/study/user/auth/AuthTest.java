package GN.study.user.auth;

import GN.study.user.dto.login.RequestLoginDto;
import GN.study.user.dto.login.ResponseLoginDto;
import GN.study.user.dto.signup.RequestUserSignUpDto;
import GN.study.user.repository.RedisRepository;
import GN.study.user.service.AuthService;
import GN.study.user.service.JwtTokenService;
import GN.study.user.service.UserService;
import GN.study.user.token.RefreshToken;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)  // JUnit 5 부터 ExtendWith 사용
@Transactional
public class AuthTest {

    @Autowired
    EntityManager em;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private RedisTemplate<String , Object> redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private RedisRepository redisRepository;

    @Test
    public void testAddBlackList(){
        // given
        String accessToken = "TESTTOKEN";
        // when
        jwtTokenService.addBlackList(accessToken);
        // then
        String addedAccessToken =  (String) redisTemplate.opsForValue().get(accessToken);

        assertEquals("blacklisted", addedAccessToken);
    }

    @Test
    public void deleteRefreshToken(){
        // given
        String refreshToken = "refreshToken";
        RefreshToken token = new RefreshToken(refreshToken, "USER1");
        // when

        // Save the token first
        redisTemplate.opsForValue().set(refreshToken, token);

        // Verify the token is saved
        assertNotNull(redisTemplate.opsForValue().get(refreshToken));

        // Delete the token
        jwtTokenService.deleteRefreshToken(refreshToken);

        // Verify the token is deleted
        assertNull(redisTemplate.opsForValue().get(refreshToken));

    }

    @Test
    public void logoutTest(){
        // given
        RequestUserSignUpDto requestUserSignUpDto1 = RequestUserSignUpDto.builder()
                .name("Kim")
                .password("password1")
                .email("maintain95@naver.com")
                .baseAddr("서울 동작구")
                .detailAddr("자이 아파트")
                .build();
        // when
        userService.createUser(requestUserSignUpDto1);

        em.flush();

        ResponseLoginDto responseLoginDto = authService.login(RequestLoginDto.builder()
                        .email("maintain95@naver.com")
                        .password("password1")
                        .build());

        em.flush();
        // then

        String accessToken = responseLoginDto.getAccessToken();
        String refreshToken = responseLoginDto.getRefreshToken();

        authService.logout(accessToken, refreshToken);
        em.flush();

        // blacklistedToken
        String findBlackListedToken = (String) redisTemplate.opsForValue().get(accessToken);

        assertEquals("blacklisted", findBlackListedToken);

        // 없으면 테스트 성공
        assertNull(refreshToken);



    }
}
