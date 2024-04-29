package GN.study.user.dto;

import GN.study.user.entity.User;
import lombok.*;

@Getter
@Builder
public class RequestUserDto {

    private Long id;

    private String name;

    private String password;

}
