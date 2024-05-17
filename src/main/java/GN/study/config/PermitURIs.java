package GN.study.config;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PermitURIs {

    // 공통 URI 리스트
    public static final List<String> URIS = List.of(
            "/api/v1/users",
            "/api/v1/auth/login",
            "/error/**",
            "/refresh/token",
            "/h2-console",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/favicon.ico",
            "/api/v1/users/check-email?**",
            "/api/v1/users/check-email",
            "/swagger-config",
            "/error"
    );

    // Security 설정에서 사용할 배열
    public static final String[] PERMIT_ALL = URIS.toArray(new String[0]);

    // Filter 설정에서 사용할 Set
    public static final Set<String> SKIP_URIS = URIS.stream().collect(Collectors.toSet());
}
