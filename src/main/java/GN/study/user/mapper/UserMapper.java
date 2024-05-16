package GN.study.user.mapper;

import GN.study.user.dto.login.RequestLoginDto;
import GN.study.user.dto.login.ResponseLoginDto;
import GN.study.user.dto.signup.RequestUserSignUpDto;
import GN.study.user.dto.signup.ResponseUserSignUpDto;
import GN.study.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

// Dto -> Entity, Entity -> Dto
@Mapper(componentModel = "spring")
public interface UserMapper {

    // RequestDto -> User
    User SingUpDtoToEntity(RequestUserSignUpDto requestUserSignUpDto);

    User LoginDtoToEntity(RequestLoginDto requestLoginDto);

    // User -> ResponseDto
    @Mapping(source = "address.baseAddr", target = "baseAddr")
    @Mapping(source = "address.detailAddr", target = "detailAddr")
    ResponseUserSignUpDto toSingUpDto(User user);

    ResponseLoginDto toResponseLoginDto(User user);

}
