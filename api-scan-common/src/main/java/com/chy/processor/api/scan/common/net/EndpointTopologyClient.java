package com.chy.processor.api.scan.common.net;


import com.chy.gamma.common.utils.LogUtils;
import com.chy.gamma.common.utils.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class EndpointTopologyClient {

    private final HttpClient httpClient;
    private  String host;
    private ObjectMapper objectMapper = new ObjectMapper();

    Logger logger = LogUtils.getLogger("EndpointTopologyClient");

    public EndpointTopologyClient(String host) {
        if (StringUtils.isEmpty(host)) {
            throw new RuntimeException("配置 {host} 不能为null");
        }

        this.host = host;
        if (!host.startsWith("http")) {
            this.host = "http://" + this.host;
        }
        this.httpClient = HttpClientBuilder.create().build();
    }

    public void send(ProjectApiSignatureFrom apiSignatureFrom) throws IOException, InterruptedException {


        sendPostRequest(host + "/api/signature", apiSignatureFrom);
    }


    private String sendPostRequest(String url, Object body) {
        String fromjson = null;
        try {
            fromjson = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("转换json失败:" + e.getMessage());
        }


        RequestConfig requestConfig = RequestConfig.custom()
                // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(5000)
                // 设置请求超时时间(单位毫秒)
                .setConnectionRequestTimeout(5000)
                // socket读写超时时间(单位毫秒)
                .setSocketTimeout(5000)
                // 设置是否允许重定向(默认为true)
                .setRedirectsEnabled(true).build();


        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        HttpEntity httpEntity = new StringEntity(fromjson, "UTF-8");
        httpPost.setEntity(httpEntity);


        logger.info("开始发送请求：" + fromjson);
        HttpResponse response;
        String responseBody = null;
        try {
            response = httpClient.execute(httpPost);
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("发送请求失败 url:[" + url + "]" + e.getMessage());
        }
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            logger.error("发送请求失败 url:[" + url + "]", responseBody);
            throw new RuntimeException("发送请求失败 url:[" + url + "] status:[" + statusCode + "]");
        }
        return responseBody;
    }


}
