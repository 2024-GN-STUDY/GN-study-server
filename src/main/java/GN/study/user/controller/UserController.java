package GN.study.user.controller;

import GN.study.user.dto.RequestUserSignUpDto;
import GN.study.user.dto.ResponseUserSignUpDto;
import GN.study.user.exception.UserExistException;
import GN.study.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User-controller", description = "회원 서비스를 위한 컨트롤러")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "회원가입 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = ResponseUserSignUpDto.class))),
            @ApiResponse(responseCode = "409", description = "이메일 중복", content = @Content()),
    })
    public ResponseEntity<ResponseUserSignUpDto> createUser(@Parameter(description = "회원가입 입력값", required = true, example = "RequestUserSignUpDto") @Valid @RequestBody RequestUserSignUpDto requestUserSignUpDto){

            ResponseUserSignUpDto responseUserSignUpDto = userService.createUser(requestUserSignUpDto);

            // hateoas 적용
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/" + responseUserSignUpDto.getId())
                    .buildAndExpand()
                    .toUri();

            return ResponseEntity.created(location).body(responseUserSignUpDto);
    }

}
