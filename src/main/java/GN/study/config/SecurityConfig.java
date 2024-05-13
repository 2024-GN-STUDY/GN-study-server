package GN.study.config;


import GN.study.user.entity.Role;
import GN.study.user.entity.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public final String[] PERMIT_URL(){
        return new String[]{
                "/api/users/",
                "/error",
                "/swagger-ui/*",
                "favicon.io",
                "/v3/api-docs/*"
        };
    }

    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(PathRequest.toH2Console());
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception{

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers(PERMIT_URL()).permitAll()
                        .requestMatchers("/").hasRole(Role.USER.name())
                )
                // X-Frame-Options 브라우저에서 iframe 에서 일어난 요청에 대해 origin 을 파악하고 요청 허용함
                // Security 에서는 기본적으로 Click Jacking 을 막고있음
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .exceptionHandling(exception -> {
                   /**
                    *       TODO :: Exception 처리
                    * */
                });
        return http.build();
    }
}
