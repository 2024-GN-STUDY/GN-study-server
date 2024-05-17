package GN.study.user.controller;

import GN.study.user.dto.signup.RequestUserSignUpDto;
import GN.study.user.dto.signup.ResponseUserSignUpDto;
import GN.study.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService  userService;

    @PostMapping
    public ResponseEntity<ResponseUserSignUpDto> createUser(@RequestBody RequestUserSignUpDto requestUserDto){
        return ResponseEntity.ok(userService.createUser(requestUserDto));
    }

    @GetMapping("/check-email")
    public ResponseEntity<HttpStatus> getUserByEmail(@RequestParam("email") String email){
            Boolean isEmail = userService.checkEmail(email);
        return ResponseEntity.ok(isEmail ? HttpStatus.OK : HttpStatus.CONFLICT);
    }
}
