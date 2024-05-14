package GN.study.user.controller;

import GN.study.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @PostMapping("/user")
//    public ResponseEntity<ResponseUserDto> createUser(RequestUserDto requestUserDto){
//        return ResponseEntity.ok(userService.createUser(requestUserDto));
//    }
}
