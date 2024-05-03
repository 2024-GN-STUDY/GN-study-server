package GN.study.user.service;

import GN.study.user.dto.RequestUserDto;
import GN.study.user.dto.ResponseUserDto;
import GN.study.user.entity.Role;
import GN.study.user.entity.User;
import GN.study.user.exception.UserExistException;
import GN.study.user.exception.UserNotFoundException;
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
    public ResponseUserDto createUser(RequestUserDto requestUserDto){

        User validUser = userRepository.findByEmail(requestUserDto.getEmail());

        // 중복 체크
        if(validUser != null){
            throw new UserExistException();
        }

        // 비밀번호 암호화
        User user = User.builder()
                .id(requestUserDto.getId())
                .name(requestUserDto.getName())
                .password(bCryptPasswordEncoder.encode(requestUserDto.getPassword()))
                .email(requestUserDto.getEmail())
                .role(Role.USER)
                .build();

        //MapStruct 사용 requestUserDto -> User Entity 로 변경
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<ResponseUserDto> findAll(){

        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
