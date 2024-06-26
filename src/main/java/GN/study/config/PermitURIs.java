package GN.study.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PermitURIs {

    // 공통 URI 리스트
    public static final List<String> URIS = List.of(
            "/api/v1/users",
            "/api/v1/auth/login",
            "/error/**",
            "/refresh/token",
            "/h2-console",
            "/swagger-ui/**",
            "/swagger-ui",
            "/v3/api-docs/**",
            "/v3/api-docs",
            "/favicon.ico",
            "/api/v1/users/check-email?**",
            "/api/v1/users/check-email",
            "/swagger-config",
            "/error",
            "/shorted/**",
            "/shorted"
    );

    // Security 설정에서 사용할 배열
    public static final String[] PERMIT_ALL = URIS.toArray(new String[0]);

    // Filter 설정에서 사용할 Set
    public static final Set<String> SKIP_URIS = new HashSet<>(URIS);
}
