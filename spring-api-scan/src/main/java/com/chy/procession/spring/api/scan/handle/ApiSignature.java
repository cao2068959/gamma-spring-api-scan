package com.chy.procession.spring.api.scan.handle;

import lombok.Data;
import lombok.ToString;

@Data
public class ApiSignature {
    String methodType;
    String url;
    String signature;


    public ApiSignature(String methodType, String url, String signature) {
        this.methodType = methodType;
        this.url = url;
        this.signature = signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiSignature that = (ApiSignature) o;

        if (!methodType.equals(that.methodType)) return false;
        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        int result = methodType.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "methodType='" + methodType + '\'' +
                ", url='" + url + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}