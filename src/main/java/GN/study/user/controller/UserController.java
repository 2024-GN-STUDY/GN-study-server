package GN.study.user.controller;

import GN.study.user.dto.RequestUserDto;
import GN.study.user.dto.ResponseUserDto;
import GN.study.user.entity.User;
import GN.study.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<ResponseUserDto> createUser(@Valid @RequestBody RequestUserDto requestUserDto){

        ResponseUserDto responseUserDto = userService.createUser(requestUserDto);

        // hateoas 적용
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/" + responseUserDto.getId())
                .buildAndExpand()
                .toUri();

        return ResponseEntity.created(location).body(responseUserDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<ResponseUserDto>> findAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }


}
