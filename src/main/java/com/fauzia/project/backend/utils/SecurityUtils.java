package com.fauzia.project.backend.utils;

public class SecurityUtils {
    public static final String SECRET = "5FB9F4E53CE3A83D17B1CB4B71AC7535C4E184C18EDC5704C988C38BF4B756CE";
    public static final String REFRESH_SECRET = "8BE521B547929BDA71E86552BA38D70A82EA5F89941A98AABFE9833ACFF76A95";

    public static final long EXPIRATION_TIME = 60000 * 60 * 24 ; // 1days

    public static final long REFRESH_EXPIRATION_TIME = 60000 * 60 * 24 * 14	; // 30days

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String REFRESH_PREFIX = "Refresh ";
    public static final String HEADER_STRING = "Authorization";
}
