package com.lynch.service;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by lynch on 2019/3/11. <br>
 **/
@Service
public class HttpPostService {
    public String[] match() {
        return new String[]{
                "register.req",
                "read.req",
                "readMany.req",
                "write.req",
                "writeMany.req",
                "setAttribute.req",
                "setAttributeMany.req",
                "setHint.req",
        };
    }

    public String requestPost(String url, String body) {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(body);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            return IOUtils.toString(response.getEntity().getContent());
        } catch (Exception e) {
            // handle exception here
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
            }
        }
        return null;
    }
}
