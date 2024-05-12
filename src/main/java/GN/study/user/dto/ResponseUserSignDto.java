package GN.study.user.dto;

import lombok.*;

@Getter
@Builder
public class ResponseUserSignDto {

    private Long id;

    private String name;

    private String email;

    private String phone_num;

    private Integer age;

    private String baseAddr;

    private String detailAddr;

}
