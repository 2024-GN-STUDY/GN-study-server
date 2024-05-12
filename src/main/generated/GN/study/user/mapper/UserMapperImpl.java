package GN.study.user.mapper;

import GN.study.user.dto.RequestUserSignUpDto;
import GN.study.user.dto.ResponseUserSignUpDto;
import GN.study.user.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-12T22:42:52+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(RequestUserSignUpDto requestUserSignUpDto) {
        if ( requestUserSignUpDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( requestUserSignUpDto.getName() );
        user.password( requestUserSignUpDto.getPassword() );
        user.email( requestUserSignUpDto.getEmail() );
        user.phone_num( requestUserSignUpDto.getPhone_num() );
        user.age( requestUserSignUpDto.getAge() );
        user.birth_dt( requestUserSignUpDto.getBirth_dt() );

        return user.build();
    }

    @Override
    public ResponseUserSignUpDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        ResponseUserSignUpDto.ResponseUserSignUpDtoBuilder responseUserSignUpDto = ResponseUserSignUpDto.builder();

        responseUserSignUpDto.id( user.getId() );
        responseUserSignUpDto.email( user.getEmail() );
        responseUserSignUpDto.name( user.getName() );
        responseUserSignUpDto.phone_num( user.getPhone_num() );
        responseUserSignUpDto.age( user.getAge() );
        responseUserSignUpDto.birth_dt( user.getBirth_dt() );
        responseUserSignUpDto.role( user.getRole() );

        return responseUserSignUpDto.build();
    }
}
