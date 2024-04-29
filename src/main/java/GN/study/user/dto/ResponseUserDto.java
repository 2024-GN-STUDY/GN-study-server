package GN.study.user.dto;

import GN.study.user.entity.User;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@Builder
public class ResponseUserDto {

    private Long id;

    private String name;

    public static ResponseUserDto toDto(User user){
        return new ResponseUserDto(user.getId(), user.getName());
    }

}
