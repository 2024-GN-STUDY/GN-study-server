package GN.study.user.service;

import GN.study.user.dto.signup.ResponseUserSignUpDto;
import GN.study.user.entity.Address;
import GN.study.user.entity.User;
import GN.study.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)  // JUnit 5 부터 ExtendWith 사용
@Transactional
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSignUpDtoToEntity(){
        Address address = new Address("123 Main St", "Apt 4B");
        User user = User.builder()
                .id(1L)
                .name("John Doe")
                .password("password")
                .email("john.doe@example.com")
                .address(address)
                .build();

        ResponseUserSignUpDto dto = userMapper.toSingUpDto(user);

        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getAddress().getBaseAddr(), dto.getBaseAddr());
        assertEquals(user.getAddress().getDetailAddr(), dto.getDetailAddr());
    }
}
