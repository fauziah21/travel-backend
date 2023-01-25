package com.fauzia.project.backend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class TokenUtils {
    public static String generateRefreshToken(String subject) { //expire refresh token 1 bulan
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityUtils.REFRESH_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityUtils.REFRESH_SECRET.getBytes()));
    }

    public static String generateToken(String subject) { // expired 1 minggu
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityUtils.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityUtils.SECRET.getBytes()));
    }

    public static boolean verifyToken(String token){
        try {
            String user = JWT.require(Algorithm.HMAC512(SecurityUtils.SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
            return user != null;
        }catch (Exception e){
            return false;
        }
    }

    public static String claimTokenSub(String token){
        try{
            String[] split = token.split(" ");
            String user = JWT.require(Algorithm.HMAC512(SecurityUtils.SECRET.getBytes()))
                    .build()
                    .verify(split[1])
                    .getSubject();
            return user;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static Integer claimToken(String token, String claim){
        try{
            String[] split = token.split(" ");
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecurityUtils.SECRET.getBytes()))
                    .build()
                    .verify(split[1]);

            Claim content = decodedJWT.getClaim(claim);

            return content.asInt();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    //claim token with custom claim for refresh token
    public static Integer claimTokenRefresh(String token, String claim){
        try{
            String[] split = token.split(" ");
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecurityUtils.REFRESH_SECRET.getBytes()))
                    .build()
                    .verify(split[0]);

            Claim content = decodedJWT.getClaim(claim);

            return content.asInt();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    //claim token for refresh token
    public static String claimTokenSubRefresh(String token){
        try{
            String[] split = token.split(" ");
            String user = JWT.require(Algorithm.HMAC512(SecurityUtils.REFRESH_SECRET.getBytes()))
                    .build()
                    .verify(split[0])
                    .getSubject();
            return user;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
