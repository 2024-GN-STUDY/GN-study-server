package GN.study.user.mapper;

import GN.study.user.dto.RequestUserSignDto;
import GN.study.user.dto.ResponseUserSignDto;
import GN.study.user.entity.User;
import org.mapstruct.Mapper;

// Dto -> Entity, Entity -> Dto
@Mapper(componentModel = "spring")
public interface UserMapper {

    // RequestDto -> User
    User toEntity(RequestUserSignDto requestUserSignDto);

    // USer -> ResponseDto
    ResponseUserSignDto toDto(User user);

}
