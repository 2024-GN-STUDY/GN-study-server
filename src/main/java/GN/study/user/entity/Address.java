package GN.study.user.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Address {

    // 기본 주소
    private final String baseAddr;

    // 상세 주소
    private final String detailAddr;
}
