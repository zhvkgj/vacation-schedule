package com.simple.task.vacationschedule.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class TokenAuthenticationService {

    private final UserDetailsService userDetailsService;

    static final long EXPIRATION_TIME = 864_000_000;

    static final String SECRET = "ThisIsASecret";

    static final String HEADER_STRING = "Authorization";

    @Autowired
    public TokenAuthenticationService(UserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void addAuthentication(HttpServletResponse res, String username) {
        String JWT = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        res.addHeader(HEADER_STRING, JWT);
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {

            String user = Jwts
                    .parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            UserDetails userDetails = userDetailsService.loadUserByUsername(user);
            return user != null ? new UsernamePasswordAuthenticationToken(userDetails, "",
                            userDetails.getAuthorities()) : null;
        }

        return null;
    }
}