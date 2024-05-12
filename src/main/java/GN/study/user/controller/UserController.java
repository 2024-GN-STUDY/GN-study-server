package GN.study.user.controller;

import GN.study.user.dto.RequestUserSignDto;
import GN.study.user.dto.ResponseUserSignDto;
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
    public ResponseEntity<ResponseUserSignDto> createUser(@Valid @RequestBody RequestUserSignDto requestUserSignDto){

        ResponseUserSignDto responseUserSignDto = userService.createUser(requestUserSignDto);

        // hateoas 적용
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/" + responseUserSignDto.getId())
                .buildAndExpand()
                .toUri();

        return ResponseEntity.created(location).body(responseUserSignDto);
    }

    @GetMapping
    public ResponseEntity<List<ResponseUserSignDto>> findAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }


}
