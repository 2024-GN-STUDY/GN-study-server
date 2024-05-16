package GN.study.user.service;

import GN.study.user.dto.signup.RequestUserSignUpDto;
import GN.study.user.dto.signup.ResponseUserSignUpDto;
import GN.study.user.entity.Role;
import GN.study.user.exception.UserExistException;
import GN.study.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)  // JUnit 5 부터 ExtendWith 사용
@Transactional
public class UserServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void 회원가입_테스트() throws Exception{
        em.clear();
        //given
        RequestUserSignUpDto requestUserSignUpDto1 = RequestUserSignUpDto.builder()
                .name("Kim")
                .password("password1")
                .email("maintain95@naver.com")
                .baseAddr("서울 동작구")
                .detailAddr("자이 아파트")
                .role(Role.USER)
                .build();

        //when
        ResponseUserSignUpDto responseUserSignUpDto1 = userService.createUser(requestUserSignUpDto1);
        em.flush();

        //then
        assertEquals(responseUserSignUpDto1.getId(), userRepository.findByEmail("maintain95@naver.com").orElseThrow().getId());
        assertEquals("서울 동작구", responseUserSignUpDto1.getBaseAddr());
        assertEquals(responseUserSignUpDto1.getBaseAddr(), userRepository.findByEmail("maintain95@naver.com").orElseThrow().getAddress().getBaseAddr());
        assertEquals(responseUserSignUpDto1.getDetailAddr(), userRepository.findByEmail("maintain95@naver.com").orElseThrow().getAddress().getDetailAddr());
    }

    @Test
    public void 회원가입_이메일_중복_테스트(){

        //given
        RequestUserSignUpDto requestUserSignUpDto1 = RequestUserSignUpDto.builder()
                .name("Yu")
                .password("password1")
                .email("maintain98@naver.com")
                .baseAddr("서울 동작구")
                .detailAddr("자이 아파트")
                .role(Role.USER)
                .build();

        RequestUserSignUpDto requestUserSignUpDto2 = RequestUserSignUpDto.builder()
                .name("Kim")
                .password("password2")
                .email("maintain98@naver.com")      // 동일한 이메일 입력시
                .baseAddr("서울 동작구")
                .detailAddr("자이 아파트")
                .role(Role.USER)
                .build();

        //when
        ResponseUserSignUpDto responseUserSignUpDto1 = userService.createUser(requestUserSignUpDto1);

        em.flush();

        Exception exception = assertThrows(UserExistException.class, () -> {
            userService.createUser(requestUserSignUpDto2);          // 409 conflict 에러 발생해야함
        });

        //then
        String errorMessage = "Already Exist User";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(errorMessage));
    }


    @Test
    public void 이메일_중복_확인(){
        //given
        RequestUserSignUpDto requestUserSignUpDto1 = RequestUserSignUpDto.builder()
                .name("Yu")
                .password("password1")
                .email("maintain98@naver.com")
                .role(Role.USER)
                .build();

        // when
        userService.createUser(requestUserSignUpDto1);
        em.flush();

        // then
        assertEquals(userService.checkEmail("maintain95@naver.com"), true);
        assertThrows(UserExistException.class, () -> {
            userService.checkEmail("maintain98@naver.com");
        });
    }
}