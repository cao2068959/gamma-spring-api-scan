package com.chy.procession.spring.api.scan.mapping.annotationmappingImp;

import com.chy.procession.spring.api.scan.mapping.MappingItemResult;
import com.chy.processor.api.scan.common.utils.Lists;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.ArrayList;
import java.util.List;


public class PatchMappingHandle extends AbstractSpringAnnotationMapping<PatchMapping> {

    @Override
    public List<MappingItemResult> pasre(PatchMapping requestMapping) {
        List<String> requestMappingPath = getRequestMappingPath(requestMapping);

        List<MappingItemResult> result = new ArrayList<>();
        for (String path : requestMappingPath) {
            MappingItemResult mappingItemResult = new MappingItemResult("PATCH", path);
            result.add(mappingItemResult);
        }
        return result;
    }

    @Override
    public Class<PatchMapping> getAnnotationClassType() {
        return PatchMapping.class;
    }

    private List<String> getRequestMappingPath(PatchMapping requestMapping) {
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