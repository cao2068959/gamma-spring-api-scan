package com.chy.procession.spring.api.scan.mapping.annotationmappingImp;

import com.chy.procession.spring.api.scan.mapping.MappingItemResult;
import com.chy.processor.api.scan.common.utils.Lists;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class PostMappingHandle extends AbstractSpringAnnotationMapping<PostMapping> {


    @Override
    public List<MappingItemResult> pasre(PostMapping requestMapping) {
        List<String> requestMappingPath = getRequestMappingPath(requestMapping);

        List<MappingItemResult> result = new ArrayList<>();
        for (String path : requestMappingPath) {
            MappingItemResult mappingItemResult = new MappingItemResult("POST", path);
            result.add(mappingItemResult);
        }
        return result;
    }

    @Override
    public Class<PostMapping> getAnnotationClassType() {
        return PostMapping.class;
    }


    private List<String> getRequestMappingPath(PostMapping requestMapping) {
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
