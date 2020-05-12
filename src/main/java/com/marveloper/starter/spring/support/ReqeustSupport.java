package com.marveloper.starter.spring.support;

import org.springframework.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;

public class ReqeustSupport {
        /**
         * Request 에서 Body Data를 꺼냄
         * @param request
         * @return
         */
        public static String getBody( HttpServletRequest request) {

            String charEncoding = request.getCharacterEncoding(); // 인코딩 설정
            Charset encoding = StringUtils.isEmpty(charEncoding) ? StandardCharsets.UTF_8 : Charset.forName(charEncoding);

            String body = null;
            try {

                InputStream is = request.getInputStream();
                byte[] rawData = IOUtils.toByteArray(is); // InputStream 을 별도로 저장한 다음 getReader() 에서 새 스트림으로 생성

                // body 파싱
                body = new BufferedReader(new InputStreamReader( getInputStream( rawData), encoding)).lines().collect(Collectors.joining(System.lineSeparator()));
                if (StringUtils.isEmpty(body)) { // body 가 없을경우 로깅 제외
                    return null;
                }
            } catch( Exception e) {
                e.printStackTrace();
            }
            return body;
        }

        private static ServletInputStream getInputStream(byte[] rawData) throws IOException {

            final ByteArrayInputStream bais = new ByteArrayInputStream( rawData);
            ServletInputStream sis = new ServletInputStream() {

                @Override
                public int read() throws IOException {
                    return bais.read();
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public boolean isFinished() {
                    return false;
                }
            };

            return sis;
        }
    }
