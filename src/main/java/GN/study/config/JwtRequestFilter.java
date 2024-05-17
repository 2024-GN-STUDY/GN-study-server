package GN.study.config;

import GN.study.user.service.CustomUserDetailsService;
import GN.study.user.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
public class JwtRequestFilter  extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final CustomUserDetailsService userDetailsService;

    private final JwtTokenService jwtTokenService;

    // 로그인, 회원가입, 엑세스토큰 재발급
    private static final Set<String> SKIP_URIS = Set.of(
            "/users/signup",
            "/users/login",
            "/refresh/token",
            "/h2-console",
            "/swagger-ui",
            "/favicon.ico",
            "/v3/api-docs",
            "/swagger-config"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String filterSkipPath = request.getRequestURI();

        // 로그인, 회원가입은 필터적용 X, anyMatch - 하나라도 포함되면 더 이상 실행 X
        if(SKIP_URIS.stream().anyMatch(filterSkipPath::contains)){
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");        // Header 에서 Authorization 정보를 가져옴

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            // Bearer 타입이 아니거나, 토큰이 없으면 401 Unauthorized 응답
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = authorizationHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        String type = jwtUtil.extractType(token);

        // 리프레시 토큰일 경우 검증 스킵
        if("refresh".equals(type)){
            filterChain.doFilter(request, response);
            return;
        }

        //블랙리스트 인지 체크
        if(jwtTokenService.isTokenBlacklisted(token)){
            return;
        }

        // 엑세스토큰 의 경우 사용자 정보 검증
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            if(jwtUtil.validateToken(token, username)){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else{
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid Token");
                return;
            }

        }

        filterChain.doFilter(request, response);

    }
}