package com.chy.procession.spring.api.scan.mapping.annotationmappingImp;

import com.chy.procession.spring.api.scan.mapping.MappingItemResult;
import com.chy.processor.api.scan.common.utils.Lists;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.List;


public class PutMappingHandle extends AbstractSpringAnnotationMapping<PutMapping> {


    @Override
    public List<MappingItemResult> pasre(PutMapping requestMapping) {
        List<String> requestMappingPath = getRequestMappingPath(requestMapping);

        List<MappingItemResult> result = new ArrayList<>();
        for (String path : requestMappingPath) {
            MappingItemResult mappingItemResult = new MappingItemResult("PUT", path);
            result.add(mappingItemResult);
        }
        return result;
    }

    @Override
    public Class<PutMapping> getAnnotationClassType() {
        return PutMapping.class;
    }


    private List<String> getRequestMappingPath(PutMapping requestMapping) {
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
