package GN.study.user.mapper;

import GN.study.user.dto.RequestUserDto;
import GN.study.user.dto.ResponseUserDto;
import GN.study.user.entity.User;
import org.mapstruct.Mapper;

// Dto -> Entity, Entity -> Dto
@Mapper(componentModel = "spring")
public interface UserMapper {

    // RequestDto -> User
    User toEntity(RequestUserDto requestUserDto);

    // USer -> ResponseDto
    ResponseUserDto toDto(User user);

}
