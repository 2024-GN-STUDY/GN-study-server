package GN.study.user.dto;

import GN.study.user.entity.User;
import lombok.*;

@Getter @Setter
@RequiredArgsConstructor
public class RequestUserDto {

    private final Long id;

    private final String name;

    private final String password;

    @Builder
    public static User toEntity(RequestUserDto requestUserDto){
        return User.builder()
                .id(requestUserDto.getId())
                .name(requestUserDto.getName())
                .password(requestUserDto.getPassword())
                .build();
    }
}
