package GN.study.user.service;

import GN.study.jwt.service.JwtService;
import GN.study.user.dto.login.RequestLoginDto;
import GN.study.user.dto.login.ResponseLoginDto;
import GN.study.user.dto.signup.RequestUserSignUpDto;
import GN.study.user.dto.signup.ResponseUserSignUpDto;
import GN.study.user.entity.Address;
import GN.study.user.entity.User;
import GN.study.user.exception.UserNotFoundException;
import GN.study.user.mapper.UserMapper;
import GN.study.user.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public ResponseUserSignUpDto createUser(RequestUserSignUpDto requestUserDto){
        User user = User.builder()
                .name(requestUserDto.getName())
                .password(bCryptPasswordEncoder.encode(requestUserDto.getPassword()))
                .email(requestUserDto.getEmail())
                .age(requestUserDto.getAge())
                .birth_dt(requestUserDto.getBirth_dt())
                .phone_num(requestUserDto.getPhone_num())
                .address(new Address(requestUserDto.getBaseAddr(), requestUserDto.getDetailAddr()))
                .role(requestUserDto.getRole())
                .build();

        return userMapper.toSingUpDto(userRepository.save(user));
    }

    @Transactional
    public ResponseLoginDto login(RequestLoginDto requestLoginDto){
        return jwtService.createToken(requestLoginDto);
    }

}
