package com.chy.procession.spring.api.scan.mapping.annotationmappingImp;

import com.chy.procession.spring.api.scan.mapping.MappingItemResult;
import com.chy.processor.api.scan.common.utils.Lists;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class RequestMappingHandle extends AbstractSpringAnnotationMapping<RequestMapping> {
    @Override
    public List<MappingItemResult> pasre(RequestMapping requestMapping) {
        RequestMethod[] method = requestMapping.method();
        List<String> methodTypeList = requestMethodList(method);
        List<String> requestMappingPath = getRequestMappingPath(requestMapping);

        List<MappingItemResult> result = new ArrayList<>();
        for (String methodType : methodTypeList) {
            for (String path : requestMappingPath) {
                MappingItemResult mappingItemResult = new MappingItemResult(methodType, path);
                result.add(mappingItemResult);
            }
        }
        return result;
    }

    @Override
    public Class<RequestMapping> getAnnotationClassType() {
        return RequestMapping.class;
    }


    private List<String> getRequestMappingPath(RequestMapping requestMapping) {
        if (requestMapping == null) {
            return Lists.of();
        }
        String[] value = requestMapping.value();
        if (value.length != 0) {
            return Lists.of(value);
        }

        value = requestMapping.path();
        if (value.length != 0) {
            return Lists.of(value);
        }
        return Lists.of();
    }

    @Override
    public RequestMapping getAnnotation(Method method) {
        return AnnotationUtils.getAnnotation(method,RequestMapping.class);
    }
}
