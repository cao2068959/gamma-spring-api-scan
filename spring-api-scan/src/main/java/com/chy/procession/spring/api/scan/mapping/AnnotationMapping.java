package com.chy.procession.spring.api.scan.mapping;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public interface AnnotationMapping<T extends Annotation> {

    List<MappingItemResult> pasre(T t);

    Class<T> getAnnotationClassType();

    default T getAnnotation(Method method) {
        return method.getDeclaredAnnotation(getAnnotationClassType());
    }
}
