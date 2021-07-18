package com.chy.procession.spring.api.scan.mapping;


import com.chy.processor.api.scan.common.utils.Lists;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MappingResult {

    @Getter
    Set<MappingItemResult> data = new HashSet<>();

    public void merge(List<MappingItemResult> mappingItemResult, List<String> urlPrefixs) {
        if (urlPrefixs == null || urlPrefixs.size() == 0) {
            urlPrefixs = Lists.of("");
        }
        for (MappingItemResult itemResult : mappingItemResult) {
            for (String urlPrefix : urlPrefixs) {
                MappingItemResult mappingItem = new MappingItemResult(itemResult, urlPrefix);
                data.add(mappingItem);
            }
        }
    }

    @Override
    public String toString() {
        return "MappingResult{" +
                "data=" + data +
                '}';
    }

}
