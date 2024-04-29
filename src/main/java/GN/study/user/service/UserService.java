package GN.study.user.service;

import GN.study.user.dto.RequestUserDto;
import GN.study.user.dto.ResponseUserDto;
import GN.study.user.entity.User;
import GN.study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public ResponseUserDto createUser(RequestUserDto requestUserDto){

        User user = RequestUserDto.toEntity(requestUserDto);

        return ResponseUserDto.toDto(userRepository.save(user));
    }

    @Transactional
    public List<ResponseUserDto> findByAll(){

        return userRepository.findAll().stream().map(ResponseUserDto::toDto).collect(Collectors.toList());
    }
}
