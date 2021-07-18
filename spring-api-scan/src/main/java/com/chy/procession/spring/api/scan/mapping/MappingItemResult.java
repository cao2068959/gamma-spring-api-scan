package com.chy.procession.spring.api.scan.mapping;


import com.chy.gamma.common.utils.StringUtils;

public class MappingItemResult {

    String methodType;
    String url;

    public MappingItemResult(String methodType, String url) {
        this.methodType = methodType;
        this.url = url;
    }

    public MappingItemResult(MappingItemResult mappingItemResult, String urlPrefix) {
        this.methodType = mappingItemResult.getMethodType();
        this.url = mappingItemResult.getUrl();
        addUrlPrefix(urlPrefix);
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        this.url = url;
    }

    private void addUrlPrefix(String urlPrefix) {
        if (StringUtils.isEmpty(urlPrefix)) {
            return;
        }
        if (urlPrefix.endsWith("/")) {
            urlPrefix = urlPrefix.substring(0, urlPrefix.length() - 1);
        }
        setUrl(urlPrefix + url);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MappingItemResult that = (MappingItemResult) o;

        if (methodType != null ? !methodType.equals(that.methodType) : that.methodType != null) return false;
        return url != null ? url.equals(that.url) : that.url == null;
    }

    @Override
    public int hashCode() {
        int result = methodType != null ? methodType.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MappingItemResult{" +
                "methodType='" + methodType + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
