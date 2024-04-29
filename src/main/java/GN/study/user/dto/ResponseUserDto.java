package GN.study.user.dto;

import GN.study.user.entity.User;
import lombok.*;

@Getter @Setter
@RequiredArgsConstructor
public class ResponseUserDto {

    private final Long id;

    private final String name;

    @Builder
    public static ResponseUserDto toDto(User user){
        return new ResponseUserDto(user.getId(), user.getName());
    }

}
