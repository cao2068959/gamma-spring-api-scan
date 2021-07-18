package com.chy.processor.api.scan.common.net;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ApiSignatureFrom {

    String methodType;
    String url;
    String signature;


    public ApiSignatureFrom(String methodType, String url, String signature) {
        this.methodType = methodType;
        this.url = url;
        this.signature = signature;
    }

}
