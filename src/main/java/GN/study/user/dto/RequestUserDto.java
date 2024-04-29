package GN.study.user.dto;

import GN.study.user.entity.User;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserDto {

    private Long id;

    private String name;

    private String password;

    public static User toEntity(RequestUserDto requestUserDto){
        return User.builder()
                .id(requestUserDto.getId())
                .name(requestUserDto.getName())
                .password(requestUserDto.getPassword())
                .build();
    }
}
