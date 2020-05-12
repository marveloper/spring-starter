package com.marveloper.starter.spring.filter;

import com.marveloper.starter.spring.filter.wrapper.UserRequestWrapper;
import com.marveloper.starter.spring.filter.wrapper.UserResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class UserFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        UserRequestWrapper userRequestWrapper = new UserRequestWrapper(request);
        log.info("[request] {}", request.getContentType());

        UserResponseWrapper userResponseWrapper = new UserResponseWrapper(response);
        filterChain.doFilter(userRequestWrapper, userResponseWrapper);
        userResponseWrapper.setContentType(MediaType.APPLICATION_JSON_VALUE);
        log.info("[response] {}", userResponseWrapper.getContentType());
    }
}
