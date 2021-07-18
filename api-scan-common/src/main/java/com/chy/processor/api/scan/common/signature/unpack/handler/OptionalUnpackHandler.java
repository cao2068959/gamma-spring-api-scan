package com.chy.processor.api.scan.common.signature.unpack.handler;


import com.chy.processor.api.scan.common.TypeUtils;

import java.lang.reflect.Type;
import java.util.Optional;

public class OptionalUnpackHandler implements UnpackHandler {

    @Override
    public boolean isHandle(Type origin) {
        Class aClass = TypeUtils.typeToClass(origin);
        return aClass != null && aClass.isAssignableFrom(Optional.class);
    }


    @Override
    public Optional<String> typeFlag(Type origin) {
        return Optional.of("Optional");
    }

    @Override
    public Optional<Type> afterType(Type origin) {
        Type genericType = TypeUtils.getGenericType(origin);
        return Optional.ofNullable(genericType);
    }
}
