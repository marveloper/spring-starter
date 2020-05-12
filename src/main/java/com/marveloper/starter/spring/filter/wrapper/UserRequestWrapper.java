package com.marveloper.starter.spring.filter.wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marveloper.starter.spring.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class UserRequestWrapper extends HttpServletRequestWrapper {
    private final Charset encoding;
    private String rawData;
    private ObjectMapper objectMapper = new ObjectMapper();

    public UserRequestWrapper(HttpServletRequest request) {
        super(request);

        String charEncoding = request.getCharacterEncoding();
        this.encoding = StringUtils.isEmpty(charEncoding) ? StandardCharsets.UTF_8 : Charset.forName(charEncoding);

        try {
            this.rawData = IOUtils.toString(request.getInputStream(), this.encoding);
            if (StringUtils.isEmpty(this.rawData)) {
                return;
            }
            User user = new User(this.rawData, "");
            this.rawData = objectMapper.writeValueAsString(user);
        } catch (Exception e) {
            log.error("ReadableRequestWrapper init error", e);
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData.getBytes());

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // Do nothing
            }

            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> headerVals = Collections.list(super.getHeaders(name));
        int index = 0;
        for (String value : headerVals) {
            if ("Content-Type".equalsIgnoreCase(name)) {
                log.info("Content type change: ");
                headerVals.set(index, MediaType.APPLICATION_JSON_VALUE);
            }
            index++;
        }
        return Collections.enumeration(headerVals);
    }
}