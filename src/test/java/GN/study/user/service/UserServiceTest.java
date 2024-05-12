package GN.study.user.service;


import GN.study.user.dto.RequestUserSignUpDto;
import GN.study.user.dto.ResponseUserSignUpDto;
import GN.study.user.entity.Address;
import GN.study.user.exception.UserExistException;
import GN.study.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
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
        //given
        RequestUserSignUpDto requestUserSignUpDto1 = RequestUserSignUpDto.builder()
                .name("Yu")
                .password("password1")
                .email("maintain98@naver.com")
                .baseAddr("서울 동작구")
                .detailAddr("자이 아파트")
                .build();

        //when
        ResponseUserSignUpDto responseUserSignUpDto = userService.createUser(requestUserSignUpDto1);

        em.flush();
        //then
        assertEquals(responseUserSignUpDto.getId(), userRepository.findByEmail("maintain98@naver.com").getId());
        assertEquals("서울 동작구", requestUserSignUpDto1.getBaseAddr());
        assertEquals(requestUserSignUpDto1.getBaseAddr(), userRepository.findByEmail("maintain98@naver.com").getAddress().getBaseAddr());
        assertEquals(requestUserSignUpDto1.getDetailAddr(), userRepository.findByEmail("maintain98@naver.com").getAddress().getDetailAddr());
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
                .build();

        RequestUserSignUpDto requestUserSignUpDto2 = RequestUserSignUpDto.builder()
                .name("Kim")
                .password("password2")
                .email("maintain98@naver.com")      // 동일한 이메일 입력시
                .baseAddr("서울 동작구")
                .detailAddr("자이 아파트")
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
}
