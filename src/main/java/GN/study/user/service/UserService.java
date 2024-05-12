package GN.study.user.service;

import GN.study.user.dto.RequestUserSignUpDto;
import GN.study.user.dto.ResponseUserSignUpDto;
import GN.study.user.entity.Address;
import GN.study.user.entity.Role;
import GN.study.user.entity.User;
import GN.study.user.exception.UserExistException;
import GN.study.user.mapper.UserMapper;
import GN.study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public ResponseUserSignUpDto createUser(RequestUserSignUpDto requestUserSignUpDto){

        User validUser = userRepository.findByEmail(requestUserSignUpDto.getEmail());

        // 중복 체크
        if(validUser != null){
            throw new UserExistException();
        }

        // 비밀번호 암호화
        User user = User.builder()
                .name(requestUserSignUpDto.getName())
                .password(bCryptPasswordEncoder.encode(requestUserSignUpDto.getPassword()))
                .email(requestUserSignUpDto.getEmail())
                .role(Role.USER)
                .age(requestUserSignUpDto.getAge())
                .birth_dt(requestUserSignUpDto.getBirth_dt())
                .phone_num(requestUserSignUpDto.getPhone_num())
                .address(new Address(requestUserSignUpDto.getBaseAddr(), requestUserSignUpDto.getDetailAddr()))
                .build();

        //MapStruct 사용 requestUserDto -> User Entity 로 변경
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<ResponseUserSignUpDto> findAll(){

        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
