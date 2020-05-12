package com.marveloper.starter.spring.filter.wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marveloper.starter.spring.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class UserResponseWrapper extends HttpServletResponseWrapper {
    private String contentType;

    public UserResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public void setContentType(String type) {
        this.contentType = type;
        super.setContentType(type);
    }

    public String getContentType() {
        return contentType;
    }


}