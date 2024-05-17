package GN.study.config;

import GN.study.user.service.CustomUserDetailsService;
import GN.study.user.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enalbed", havingValue = "true")
    public WebSecurityCustomizer webSecurityCustomizer() { // h2
        return web -> web.ignoring().requestMatchers(PathRequest.toH2Console());
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() { // 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());

        return authenticationManagerBuilder.build();
    }


    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtUtil jwtUtil, JwtTokenService jwtTokenService) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((request) -> request
                        .requestMatchers(PermitURIs.PERMIT_ALL).permitAll()
                        .anyRequest().authenticated()
                )
                //X-Frame-Options 브라우저에서 iframe 에서 일어난 요청에 대해 Origin 을 파악하고 같으면 요청을 허용하게됨
                //Spring Security 는 기본적으로 X-Frame-Options 에서 Click jacking 을 막고있음
                //*click jacking -> 해킹 기법
                .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .addFilterBefore(new JwtRequestFilter(jwtUtil, userDetailsService, jwtTokenService), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling((exception) -> {
                            //TODO :: Exception Handler
                        }
                );

        return httpSecurity.build();
    }

}