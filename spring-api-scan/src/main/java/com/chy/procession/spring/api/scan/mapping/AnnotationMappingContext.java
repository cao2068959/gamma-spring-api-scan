package com.chy.procession.spring.api.scan.mapping;


import com.chy.procession.spring.api.scan.mapping.annotationmappingImp.*;
import com.chy.processor.api.scan.common.utils.Lists;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class AnnotationMappingContext {

    public List<AnnotationMapping> annotationMappingList = Lists.of(new RequestMappingHandle(), new GetMappingHandle(),
            new DeleteMappingHandle(), new PatchMappingHandle(), new PostMappingHandle(), new PutMappingHandle());

    public MappingResult handle(Method method, RequestMapping requestMapping) {
        MappingResult result = null;
        List<String> urlPrefixs = getRequestMappingPath(requestMapping);

        for (AnnotationMapping annotationMapping : annotationMappingList) {
            Annotation annotation = annotationMapping.getAnnotation(method);
            if (annotation == null) {
                continue;
            }
            List<MappingItemResult> mappingItemResultList = annotationMapping.pasre(annotation);
            if (mappingItemResultList != null && mappingItemResultList.size() != 0) {
                if (result == null) {
                    result = new MappingResult();
                }
                result.merge(mappingItemResultList, urlPrefixs);
            }
        }
        return result;
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

}
