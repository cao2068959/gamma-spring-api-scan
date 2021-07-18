package com.chy.processor.api.scan.common.signature.unpack;


import lombok.Data;

import java.lang.reflect.Type;
import java.util.Optional;

@Data
public class UnpackResult {

    String simpleTypeName;
    Optional<Type> finalType;

    public UnpackResult(String simpleTypeName) {
        this.simpleTypeName = simpleTypeName;
        finalType = Optional.empty();
    }

    public UnpackResult(String simpleTypeName, Optional<Type> finalType) {
        this.simpleTypeName = simpleTypeName;
        this.finalType = finalType;
    }
}
