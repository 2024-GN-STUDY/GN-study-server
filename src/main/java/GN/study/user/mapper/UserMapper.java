package GN.study.user.mapper;

import GN.study.user.dto.RequestUserSignUpDto;
import GN.study.user.dto.ResponseUserSignUpDto;
import GN.study.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// Dto -> Entity, Entity -> Dto
@Mapper(componentModel = "spring")
public interface UserMapper {

    // RequestDto -> User
    User toEntity(RequestUserSignUpDto requestUserSignUpDto);

    // USer -> ResponseDto
    ResponseUserSignUpDto toDto(User user);

}
