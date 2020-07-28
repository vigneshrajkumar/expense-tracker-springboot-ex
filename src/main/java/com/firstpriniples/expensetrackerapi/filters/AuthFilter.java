package com.firstpriniples.expensetrackerapi.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.firstpriniples.expensetrackerapi.Constants;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AuthFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        /*
         * reads the bearer token from the inbound request, if not valid, thows
         * unauthorized error if valid unmarshalls it, extracts user id set as claims
         * and sets the user id in the header
         */

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null) {
            String[] authHeaderArr = authHeader.split("Bearer");
            if (authHeaderArr.length > 1 && authHeaderArr[1] != null) {
                String token = authHeaderArr[1];
                try {

                    Claims claims = Jwts.parser().setSigningKey(Constants.API_SECRET_KEY).parseClaimsJws(token)
                            .getBody();

                    httpRequest.setAttribute("userID", Integer.parseInt(claims.get("userID").toString()));

                } catch (Exception e) {
                    httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "invalid/expired token");
                    return;
                }
            } else {
                // reaches here when bearer token is malfomed
                httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be Bearer <token>");
                return;
            }
        } else {
            // reaches here when Authorization header is missing
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be provided");
            return;
        }

        // passing on
        filterChain.doFilter(servletRequest, servletResponse);

    }

}