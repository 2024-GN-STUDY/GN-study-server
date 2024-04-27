package GN.study.user.dto;

import GN.study.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class ResponseUserDto {

    private Long id;

    private String name;

    public static ResponseUserDto toDto(User user){
        return ResponseUserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

}
