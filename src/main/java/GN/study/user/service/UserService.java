package GN.study.user.service;

import GN.study.user.dto.RequestUserDto;
import GN.study.user.dto.ResponseUserDto;
import GN.study.user.entity.User;
import GN.study.user.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public ResponseUserDto createUser(RequestUserDto requestUserDto){

        User user = RequestUserDto.toEntity(requestUserDto);

        return ResponseUserDto.toDto(userRepository.save(user));
    }

    public List<ResponseUserDto> findByAll(){

        return userRepository.findAll().stream().map(ResponseUserDto::toDto).collect(Collectors.toList());
    }
}
