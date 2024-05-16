package GN.study.user.controller;

import GN.study.user.dto.login.RequestLoginDto;
import GN.study.user.dto.login.ResponseLoginDto;
import GN.study.user.dto.signup.RequestUserSignUpDto;
import GN.study.user.dto.signup.ResponseUserSignUpDto;
import GN.study.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService  userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseUserSignUpDto> createUser(@RequestBody RequestUserSignUpDto requestUserDto){
        return ResponseEntity.ok(userService.createUser(requestUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDto> login(@RequestBody RequestLoginDto requestLoginDto){
        return ResponseEntity.ok(userService.login(requestLoginDto));
    }

    @GetMapping("/signup/check-email")
    public ResponseEntity<HttpStatus> getUserByEmail(@RequestParam("email") String email){
            Boolean isEmail = userService.checkEmail(email);
        return ResponseEntity.ok(isEmail ? HttpStatus.OK : HttpStatus.CONFLICT);
    }
}
