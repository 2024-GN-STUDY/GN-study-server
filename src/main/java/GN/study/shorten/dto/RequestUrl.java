package GN.study.shorten.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class RequestUrl {

    @NotBlank
    private String originUrl;
}
