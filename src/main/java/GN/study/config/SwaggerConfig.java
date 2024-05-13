package GN.study.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@OpenAPIDefinition(info = @Info(title = "Service API 명세서",
                                version = "1.0.0"
))
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi groupedOpenApi(){
        String[] paths = {
                "/api/users/**"
        };

        return GroupedOpenApi.builder()
                .group("API 명세서")
                .pathsToMatch(paths)
                .build();
    }
}


/**
 * Swagger 3 annotations
 *
 * @Tag                                                     -> 클래스에 설명, Swagger 리소스
 * @Parameter                                               -> API 에서의 단일 매개 변수
 * @Parameters                                              -> API 에서의 복수 매개 변수
 * @Schema                                                  -> Swagger 모델에 대한 추가 정보
 * @Operation(summary="foo", description = "Bar")           -> 특정 경로에 대한 작업 (일반적으로 컨트롤러에서의 HTTP 메서드를 설명)
 * @ApiResponse(responseCode = "404", description = "Foo")  -> API 에서의 작업 처리에 대한 응답코드 설명
 *
 * */


