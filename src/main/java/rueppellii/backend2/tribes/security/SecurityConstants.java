package rueppellii.backend2.tribes.security;

public class SecurityConstants {
    public static final String TOKEN_SIGNING_KEY = "xm8EV6Hy5RMFK4EEACIDAwQus";
    public static final Long EXPIRATION_TIME = 864_000_000L; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    public static final String HEADER_PREFIX = "Bearer ";

//    public static final String FORM_BASED_LOGIN_ENTRY_POINT = "/api/auth/login";
//    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/**";
//    public static final String TOKEN_REFRESH_ENTRY_POINT = "/api/auth/token";

    public static final String AUTHENTICATION_URL = "/auth/login";
    public static final String PERMIT_ALL_URL = "/auth/**";
    public static final String REFRESH_TOKEN_URL = "/api/auth/token";
    public static final String API_ROOT_URL = "/api/**";
    public static final String TOKEN_ISSUER = "TRIBES2BACKEND";
    public static final Long REFRESH_TOKEN_EXP_TIME = 2_592_000_000L; //30days

}
