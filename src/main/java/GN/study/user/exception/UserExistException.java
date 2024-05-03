package GN.study.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)        // CONFLICT -> 리소스 충돌,ID 라는 PK 자원을 점유한 것에 대한 충돌이므로 사용 적합
public class UserExistException extends RuntimeException {

    public UserExistException() {
        super("Already Exist User");
    }
}
