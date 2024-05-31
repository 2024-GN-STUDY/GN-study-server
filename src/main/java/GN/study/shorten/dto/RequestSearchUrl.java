package GN.study.shorten.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestSearchUrl {

    private String shortedUrl;
}
