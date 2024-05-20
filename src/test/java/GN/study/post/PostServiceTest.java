package GN.study.post;

import GN.study.post.dto.RequestPostDto;
import GN.study.post.dto.ResponsePostDto;
import GN.study.post.service.PostService;
import GN.study.user.dto.signup.RequestUserSignUpDto;
import GN.study.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)  // JUnit 5 부터 ExtendWith 사용
@Transactional
@AutoConfigureMockMvc
public class PostServiceTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String token;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    // 토큰값 세팅
    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.token = "test-jwt-token";

        userService.createUser(RequestUserSignUpDto.builder()
                        .email("user@user.com")
                        .name("Yu")
                        .password("1234")
                        .age(10)
                        .phone_num("010-0000-0000")
                .build());
    }

    @Test
    public void createPost() throws Exception{
        // given
        RequestPostDto requestPostDto = RequestPostDto.builder()
                .subject("TEST-SUBJECT")
                .contents("TEST-CONTENTS")
                .userId(1L)
                .build();
        //when
        ResponsePostDto responsePostDto = postService.createPost(requestPostDto);

        // then
        assertEquals("TEST-SUBJECT", responsePostDto.getSubject());
        assertEquals("TEST-CONTENTS", responsePostDto.getContents());
        assertEquals(1L, responsePostDto.getUser_id());

    }
}
