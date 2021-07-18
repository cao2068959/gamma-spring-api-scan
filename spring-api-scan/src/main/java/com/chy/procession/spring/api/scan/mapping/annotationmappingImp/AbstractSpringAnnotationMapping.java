package com.chy.procession.spring.api.scan.mapping.annotationmappingImp;

import com.chy.procession.spring.api.scan.mapping.AnnotationMapping;
import com.chy.processor.api.scan.common.utils.Lists;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractSpringAnnotationMapping<T extends Annotation> implements AnnotationMapping<T> {

    protected List<String> requestMethodList(RequestMethod[] method) {
        if (method == null || method.length == 0) {
            return Lists.of("GET");
        }
        return Arrays.stream(method).map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public T getAnnotation(Method method) {
        return AnnotationUtils.getAnnotation(method, getAnnotationClassType());
    }


}
