package GN.study.user.dto;

import GN.study.user.entity.User;
import lombok.*;

@Getter
@Builder
public class ResponseUserDto {

    private Long id;

    private String name;

    private String email;

}
