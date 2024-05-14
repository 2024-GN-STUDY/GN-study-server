package GN.study.jwt.dto;

import lombok.Getter;

// 로그인 요청 DTO, JWT에 사용
@Getter
public class RequestLoginDto {

    public String name;
    public String password;
}
