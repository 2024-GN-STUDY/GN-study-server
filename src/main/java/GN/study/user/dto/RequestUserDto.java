package GN.study.user.dto;

import GN.study.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
public class RequestUserDto {

    private Long id;

    @Size(min = 1, max = 20)
    @NotBlank
    private String name;

    private String password;

}
