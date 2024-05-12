package GN.study.user.controller;

import GN.study.user.dto.RequestUserSignUpDto;
import GN.study.user.dto.ResponseUserSignUpDto;
import GN.study.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseUserSignUpDto> createUser(@Valid @RequestBody RequestUserSignUpDto requestUserSignUpDto){

        ResponseUserSignUpDto responseUserSignUpDto = userService.createUser(requestUserSignUpDto);

        // hateoas 적용
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/" + responseUserSignUpDto.getId())
                .buildAndExpand()
                .toUri();

        return ResponseEntity.created(location).body(responseUserSignUpDto);
    }

    @GetMapping
    public ResponseEntity<List<ResponseUserSignUpDto>> findAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }


}
