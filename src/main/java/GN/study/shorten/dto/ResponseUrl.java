package GN.study.shorten.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseUrl {

    private String originUrl;

    private String shortedUrl;
}
