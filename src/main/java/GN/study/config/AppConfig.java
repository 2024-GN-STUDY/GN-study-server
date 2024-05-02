package GN.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    // 비밀번호 암호화 빈 등록
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                                        // CORS를 적용할 URL 패턴 설정
                .allowedOrigins("http://localhost:3000", "http://localhost:8083")   // 허용할 URL 설정
                .allowedMethods("GET", "POST", "PUT", "DELETE")                     // 허용할 Http Method 설정
                .allowedHeaders("Authorization", "Content-Type")                    // 클라이언트 측의 CORS 요청에 허용되는 헤더 지정
                .allowCredentials(true)                                             // True 설정 시 클라이언트 요청에 credentials 포함하고 응답으로 받기 가능
                .maxAge(3600);

    }

}
