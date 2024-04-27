package GN.study.user.dto;

import GN.study.user.entity.User;
import lombok.*;

@Getter @Setter
@Builder
@RequiredArgsConstructor
public class RequestUserDto {

    private Long id;

    private String name;


    public User toEntity(RequestUserDto requestUserDto){
        return User.builder()
                .id(requestUserDto.getId())
                .name(requestUserDto.getName())
                .build();
    }
}
