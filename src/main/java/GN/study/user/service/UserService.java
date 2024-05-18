package GN.study.user.service;

import GN.study.user.dto.signup.RequestUserSignUpDto;
import GN.study.user.dto.signup.ResponseUserSignUpDto;
import GN.study.user.entity.Address;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;


    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public ResponseUserSignUpDto createUser(RequestUserSignUpDto requestUserDto){

        // Email 중복 체크  실패 시 예외처리 -> 409 Conflict
        this.checkEmail(requestUserDto.getEmail());

        User user = User.builder()
                .name(requestUserDto.getName())
                .password(bCryptPasswordEncoder.encode(requestUserDto.getPassword()))
                .email(requestUserDto.getEmail())
                .age(requestUserDto.getAge())
                .birth_dt(requestUserDto.getBirth_dt())
                .phone_num(requestUserDto.getPhone_num())
                .address(new Address(requestUserDto.getBaseAddr(), requestUserDto.getDetailAddr()))
                .role(Role.ROLE_USER)
                .build();

        return userMapper.toSingUpDto(userRepository.save(user));
    }

    public Boolean checkEmail(String email){

        // Email 중복 체크
        Optional<User> findUser = userRepository.findByEmail(email);
        if(findUser.isPresent()){
            throw new UserExistException();
        }

        return true;
    }

    public User findUser(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("USER NOT FOUND"));
    }

    public Long findUserIdByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("USER NOT FOUND")).getId();
    }

}
