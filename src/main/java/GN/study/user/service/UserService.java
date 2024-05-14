package GN.study.user.service;

import GN.study.user.entity.User;
import GN.study.user.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /*public ResponseUserDto createUser(RequestUserDto requestUserDto){
        User user = requestUserDto.toEntity(requestUserDto);
        return ResponseUserDto.toDto(userRepository.save(user));
    }*/
}
