package GN.study.user.mapper;

import GN.study.user.dto.login.RequestLoginDto;
import GN.study.user.dto.login.ResponseLoginDto;
import GN.study.user.dto.signup.RequestUserSignUpDto;
import GN.study.user.dto.signup.ResponseUserSignUpDto;
import GN.study.user.entity.User;
import org.mapstruct.Mapper;

// Dto -> Entity, Entity -> Dto
@Mapper(componentModel = "spring")
public interface UserMapper {

    // RequestDto -> User
    User SingUpDtoToEntity(RequestUserSignUpDto requestUserSignUpDto);

    User LoginDtoToEntity(RequestLoginDto requestLoginDto);

    // USer -> ResponseDto
    ResponseUserSignUpDto toSingUpDto(User user);

    ResponseLoginDto toResponseLoginDto(User user);

}
